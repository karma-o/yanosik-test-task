package org.yanosik.test.task.common.service.mapper;

import org.json.JSONObject;

/**
 * Base interface for all mappers.
 *
 * @param <M> model
 * @param <R> model response DTO
 */
public interface ResponseDtoMapper<M, R> {
    R toResponseDto(M model);

    R toResponseDto(JSONObject json);

    M toModel(R responseDto);
}
