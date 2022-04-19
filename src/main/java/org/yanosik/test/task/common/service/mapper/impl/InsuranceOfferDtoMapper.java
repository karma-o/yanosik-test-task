package org.yanosik.test.task.common.service.mapper.impl;

import java.time.LocalDateTime;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.common.model.dto.response.InsuranceOfferDto;
import org.yanosik.test.task.common.service.mapper.ResponseDtoMapper;

@Component
public class InsuranceOfferDtoMapper
        implements ResponseDtoMapper<InsuranceOffer, InsuranceOfferDto> {
    @Override
    public InsuranceOfferDto toResponseDto(InsuranceOffer model) {
        InsuranceOfferDto dto = new InsuranceOfferDto();
        dto.setId(model.getId());
        dto.setInsurer(model.getInsurer());
        dto.setPrice(model.getPrice());
        dto.setInsertTime(model.getInsertTime());
        return dto;
    }

    public InsuranceOfferDto toResponseDto(JSONObject json) {
        InsuranceOfferDto dto = new InsuranceOfferDto();
        dto.setId(json.getLong("id"));
        dto.setInsurer(json.getString("insurer"));
        dto.setPrice(json.getBigDecimal("price"));
        dto.setInsertTime(LocalDateTime.parse(json.getString("insertTime")));
        return dto;
    }

    public InsuranceOffer toModel(InsuranceOfferDto dto) {
        InsuranceOffer model = new InsuranceOffer();
        model.setId(dto.getId());
        model.setInsurer(dto.getInsurer());
        model.setPrice(dto.getPrice());
        model.setInsertTime(dto.getInsertTime());
        return model;
    }

    public String dtoToReadableString(InsuranceOfferDto insuranceOfferDto) {
        return "        Id: " + insuranceOfferDto.getId()
                + System.lineSeparator()
                + "        Company name: " + insuranceOfferDto.getInsurer()
                + System.lineSeparator()
                + "        Price: " + insuranceOfferDto.getPrice().toString()
                + System.lineSeparator()
                + "        Was added on: " + insuranceOfferDto.getInsertTime().toString()
                + System.lineSeparator()
                + "        --------------------------------------------------"
                + System.lineSeparator();
    }
}
