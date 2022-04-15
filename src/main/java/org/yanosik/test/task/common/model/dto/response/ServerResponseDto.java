package org.yanosik.test.task.common.model.dto.response;

import java.util.List;

public class ServerResponseDto {
    private List<VehicleResponseDto> vehicles;

    public List<VehicleResponseDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleResponseDto> vehicles) {
        this.vehicles = vehicles;
    }
}
