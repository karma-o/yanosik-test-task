package org.yanosik.test.task.common.service.mapper.impl;

import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.common.model.dto.response.InsuranceOfferDto;
import org.yanosik.test.task.common.service.mapper.ResponseDtoMapper;

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
}
