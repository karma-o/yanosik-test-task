package org.yanosik.test.task.server.service;

import java.util.List;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.server.dao.VehicleDao;

/**
 * contains {@link VehicleDao} and business logic of the Vehicles.
 * We don't really need this class for our task, so I did not implement it completely.
 */
public interface VehicleService {
    List<Vehicle> getByUserId(Long userId);

    //TODO: implement more methods
}
