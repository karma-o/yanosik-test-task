package org.yanosik.test.task.common.service.mapper.impl;

import java.util.stream.Collectors;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.common.model.dto.response.VehicleResponseDto;
import org.yanosik.test.task.common.service.mapper.ResponseDtoMapper;

public class VehicleDtoMapper implements ResponseDtoMapper<Vehicle, VehicleResponseDto> {
    private final InsuranceOfferDtoMapper insuranceOfferDtoMapper;

    public VehicleDtoMapper(InsuranceOfferDtoMapper insuranceOfferDtoMapper) {
        this.insuranceOfferDtoMapper = insuranceOfferDtoMapper;
    }

    @Override
    public VehicleResponseDto toResponseDto(Vehicle model) {
        VehicleResponseDto vehicleResponseDto = new VehicleResponseDto();
        vehicleResponseDto.setId(model.getId());
        vehicleResponseDto.setBrand(model.getBrand());
        vehicleResponseDto.setModel(model.getModel());
        vehicleResponseDto.setInsertTime(model.getInsertTime());
        vehicleResponseDto.setInsuranceOffers(
                model.getInsuranceOffers()
                .stream()
                .map(insuranceOfferDtoMapper::toResponseDto)
                .collect(Collectors.toList()));
        return vehicleResponseDto;
    }
}
