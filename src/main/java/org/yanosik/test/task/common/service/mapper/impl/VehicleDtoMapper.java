package org.yanosik.test.task.common.service.mapper.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yanosik.test.task.common.model.Vehicle;
import org.yanosik.test.task.common.model.dto.response.VehicleResponseDto;
import org.yanosik.test.task.common.service.mapper.ResponseDtoMapper;

/**
 * This class has a lot of different methods for mapping.
 *
 * I tried to sort of separate mapping into 3 layers:
 *   1. From JSON(which is a standard format for sending data through the internet)
 *      to DTO(which is just a wrapper for the model,
 *      used to unify the response in case we will add more fields to our model later)
 *   2. From DTO to model(and vice versa)
 *   4. From DTO to readable string for a personnel or user.
 *
 */
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

    public VehicleResponseDto toResponseDto(JSONObject vehicleJson) {
        VehicleResponseDto dto = new VehicleResponseDto();
        dto.setId(vehicleJson.getLong("id"));
        dto.setBrand(vehicleJson.getString("brand"));
        dto.setModel(vehicleJson.getString("model"));
        dto.setInsertTime(LocalDateTime.parse(vehicleJson.getString("insertTime")));
        JSONArray jsonArray = vehicleJson.getJSONArray("insuranceOffers");
        dto.setInsuranceOffers(
                IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .map(insuranceOfferDtoMapper::toResponseDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public JSONObject toJson(VehicleResponseDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dto.getId());
        map.put("brand", dto.getBrand());
        map.put("model", dto.getModel());
        map.put("insertTime", dto.getInsertTime());
        List<JSONObject> insuranceOffersJson = dto.getInsuranceOffers()
                .stream()
                .map(JSONObject::new)
                .collect(Collectors.toList());
        JSONObject result = new JSONObject(map);
        result.put("insuranceOffers", new JSONArray(insuranceOffersJson));
        return result;
    }

    public Vehicle toModel(VehicleResponseDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setInsertTime(dto.getInsertTime());
        vehicle.setInsuranceOffers(
                dto.getInsuranceOffers()
                .stream()
                .map(insuranceOfferDtoMapper::toModel)
                .collect(Collectors.toList()));
        return vehicle;
    }

    public String responseToReadableString(JSONObject serverResponse) {
        System.out.println("Mapping a server response to a readable string...");
        StringBuilder b = new StringBuilder();
        b.append("Your vehicles:").append(System.lineSeparator());
        JSONArray jsonArray = serverResponse.getJSONArray("vehicles");
        IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .map(this::toResponseDto)
                .map(this::dtoToReadableString)
                .forEach(b::append);
        return b.toString();
    }

    public String dtoToReadableString(VehicleResponseDto dto) {
        StringBuilder b = new StringBuilder();
        b.append("    Id: ").append(dto.getId())
                .append(System.lineSeparator());
        b.append("    Name: ").append(dto.getBrand()).append(" ").append(dto.getModel())
                .append(System.lineSeparator());
        b.append("    Was added on: ").append(dto.getInsertTime().toString())
                .append(System.lineSeparator());
        b.append("    Available insurance offers for this vehicle:")
                .append(System.lineSeparator());
        dto.getInsuranceOffers().forEach(
                io -> b.append(insuranceOfferDtoMapper.dtoToReadableString(io)));
        b.append("    ------------------------------------------------------")
                .append(System.lineSeparator());
        return b.toString();
    }
}
