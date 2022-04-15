package org.yanosik.test.task.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.server.dao.VehicleDao;
import org.yanosik.test.task.server.exception.DataProcessingException;
import org.yanosik.test.task.server.service.InsuranceOfferService;
import org.yanosik.test.task.server.util.ConnectionUtil;

public class VehicleDaoImpl implements VehicleDao {
    private final InsuranceOfferService insuranceOfferService;

    public VehicleDaoImpl(InsuranceOfferService insuranceOfferService) {
        this.insuranceOfferService = insuranceOfferService;
    }

    @Override
    public List<Vehicle> getByUserId(Long id) {
        String query = "SELECT id, brand, model, insert_time "
                + "FROM vehicles "
                + "WHERE user_id = ? AND is_deleted = FALSE";
        List<Vehicle> vehicles = new ArrayList<>();
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(parseVehicle(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get vehicles of userId " + id, e);
        }
        vehicles.forEach(
                vehicle -> vehicle.setInsuranceOffers(
                        insuranceOfferService.getByVehicleId(
                                vehicle.getId())));
        return vehicles;
    }

    private Vehicle parseVehicle(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(resultSet.getObject(1, Long.class));
        vehicle.setBrand(resultSet.getString(2));
        vehicle.setModel(resultSet.getString(3));
        vehicle.setInsertTime(
                        resultSet.getTimestamp(4).toLocalDateTime());
        return vehicle;
    }
}
