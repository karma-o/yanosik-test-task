package org.yanosik.test.task.server.dao;

import java.util.List;
import org.yanosik.test.task.common.model.Vehicle;

/**
 * Would be used to communicate with database of the Vehicles.
 * We don't really need this class for our task, so I did not implement it completely.
 */
public interface VehicleDao {
    List<Vehicle> getByUserId(Long id);

    //TODO: implement more methods
}
