package org.yanosik.test.task.common.service.mapper;

import org.json.JSONObject;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;

/**
 * Base interface for all mappers.
 *
 * @param <M> model
 * @param <R> model Request DTO
 */
public interface RequestDtoMapper<M, R> {

    /**
     * Converts model to request DTO.
     *
     * @param model model
     * @return request DTO
     */
    R toRequestDto(M model);

    /**
     * Converts
     *
     * @param json formatted string
     * @return request DTO
     */
    UserRequestDto toRequestDto(JSONObject json);
}
