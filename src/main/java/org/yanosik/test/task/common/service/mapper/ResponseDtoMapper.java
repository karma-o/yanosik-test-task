package org.yanosik.test.task.common.service.mapper;

/**
 * Base interface for all mappers.
 *
 * @param <M> model
 * @param <R> model response DTO
 */
public interface ResponseDtoMapper<M, R> {

    /**
     * Maps model to response DTO.
     *
     * @param model model
     * @return response DTO
     */
    R toResponseDto(M model);
}
