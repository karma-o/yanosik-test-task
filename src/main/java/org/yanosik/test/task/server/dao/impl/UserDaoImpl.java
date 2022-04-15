package org.yanosik.test.task.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.server.dao.UserDao;
import org.yanosik.test.task.server.exception.DataProcessingException;
import org.yanosik.test.task.server.service.VehicleService;
import org.yanosik.test.task.server.util.ConnectionUtil;

/**
 * We could have used JOINs in our queries to initialize every field of the User,
 * but with current structure of the database it is a bit complicated, so I used services here.
 * First, we have to bring our database to the 3rd normal Form
 * (Which is basically a standard these days),
 * That means:
 *   - entries in the data cell must be atomic.(this may be violated in the current structure)
 *   - every record should have a unique identifier (PK).
 *   - there must be no partial dependencies. Every field must be related to its PK
 *     (originally, user_login was FK in the Vehicle table, which is not acceptable).
 *   - there should be no transitive dependencies.
 * So, I have implemented part of this structure in the database + minor improvement,
 * such as soft delete in case something goes wrong.
 *
 */
public class UserDaoImpl implements UserDao {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final VehicleService vehicleService;

    public UserDaoImpl(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (nick, login, password, insert_time) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createUserStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            createUserStatement.setString(1, user.getNick());
            createUserStatement.setString(2, user.getLogin());
            createUserStatement.setString(3, user.getPassword());
            user.setInsertTime(LocalDateTime.now());
            createUserStatement.setString(4, user.getInsertTime().format(formatter));
            createUserStatement.execute();
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create user: "
                    + user, e);
        }
        if (user.getVehicles() != null) {
            insertVehicles(user);
        }
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        String query = "SELECT id, nick, login, password, insert_time "
                + "FROM users "
                + "WHERE id = ? AND is_deleted = FALSE";
        User user = null;
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = parseUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by id " + id, e);
        }
        if (user != null) {
            user.setVehicles(
                    vehicleService.getByUserId(
                            user.getId()));
        }
        return Optional.ofNullable(user);
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getObject("id", Long.class));
        user.setNick(resultSet.getString("nick"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setInsertTime(
                        resultSet.getTimestamp("insert_time").toLocalDateTime());
        return user;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users "
                + "SET nick = ?, login = ?, password = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getNick());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update "
                    + user + " in users table.", e);
        }
        // here we should have used a set of methods to remove relation with old vehicles,
        // (except for the ones that are still in the list)
        // and then insert new ones with their relations in the separate users_vehicles table
        // (this table would be the best solution and structural modification of our database,
        // despite that we can still use current database)
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET is_deleted = TRUE WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't soft delete user with id " + id, e);
        }
    }

    private void insertVehicles(User user) {
        String query = "INSERT INTO vehicles (user_id, brand, model, insert_time) "
                + "VALUES (?, ?, ?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet;
            for (Vehicle vehicle : user.getVehicles()) {
                statement.setLong(1, user.getId());
                statement.setString(2, vehicle.getBrand());
                statement.setString(3, vehicle.getModel());
                vehicle.setInsertTime(LocalDateTime.now());
                statement.setString(4, vehicle.getInsertTime().format(formatter));
                statement.execute();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    vehicle.setId(resultSet.getObject(1, Long.class));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't insert vehicles for user " + user, e);
        }
        user.getVehicles().forEach(this::insertInsuranceOffers);
    }

    private void insertInsuranceOffers(Vehicle vehicle) {
        String query = "INSERT INTO insurance_offers (vehicle_id, insurer, price, insert_time) "
                + "VALUES (?, ?, ?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet;
            for (InsuranceOffer insuranceOffer : vehicle.getInsuranceOffers()) {
                statement.setLong(1, insuranceOffer.getVehicleId());
                statement.setString(2, insuranceOffer.getInsurer());
                statement.setBigDecimal(3, insuranceOffer.getPrice());
                insuranceOffer.setInsertTime(LocalDateTime.now());
                statement.setString(4, insuranceOffer.getInsertTime().format(formatter));
                statement.execute();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    insuranceOffer.setId(resultSet.getObject(1, Long.class));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Couldn't insert insurance offers for vehicle " + vehicle, e);
        }
    }
}
