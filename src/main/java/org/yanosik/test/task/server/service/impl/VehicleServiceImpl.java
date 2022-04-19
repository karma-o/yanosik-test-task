package org.yanosik.test.task.server.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.server.dao.VehicleDao;
import org.yanosik.test.task.server.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleDao vehicleDao;

    public VehicleServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public List<Vehicle> getByUserId(Long userId) {
        return vehicleDao.getByUserId(userId);
    }
}
