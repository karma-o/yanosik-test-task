package org.yanosik.test.task.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.server.dao.InsuranceOfferDao;
import org.yanosik.test.task.server.exception.DataProcessingException;
import org.yanosik.test.task.server.util.ConnectionUtil;

public class InsuranceOfferDaoImpl implements InsuranceOfferDao {
    @Override
    public List<InsuranceOffer> getByVehicleId(Long id) {
        String query = "SELECT id, insurer, price, insert_time "
                + "FROM insurance_offers "
                + "WHERE vehicle_id = ? AND is_deleted = FALSE";
        List<InsuranceOffer> insuranceOffers = new ArrayList<>();
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                insuranceOffers.add(parseInsuranceOffer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get insuranceOffers of vehicleId " + id, e);
        }
        System.out.println("Setting insurance for vehicle with id = " + id);
        return insuranceOffers;
    }

    private InsuranceOffer parseInsuranceOffer(ResultSet resultSet) throws SQLException {
        InsuranceOffer insuranceOffer = new InsuranceOffer();
        insuranceOffer.setId(resultSet.getLong("id"));
        insuranceOffer.setInsurer(resultSet.getString("insurer"));
        insuranceOffer.setPrice(resultSet.getBigDecimal("price"));
        insuranceOffer.setInsertTime(resultSet.getTimestamp("insert_time").toLocalDateTime());
        return insuranceOffer;
    }
}
