package org.yanosik.test.task.common.service.mapper.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;
import org.yanosik.test.task.common.service.mapper.RequestDtoMapper;

@Component
public class UserDtoMapper implements RequestDtoMapper<User, UserRequestDto> {

    @Override
    public UserRequestDto toRequestDto(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(user.getId());
        userRequestDto.setPassword(user.getPassword());
        return userRequestDto;
    }

    public UserRequestDto toRequestDto(JSONObject json) {
        System.out.println("Mapping request to DTO... ");
        // json example: {"id":1, "password":"qwerty123"}
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(json.getLong("id"));
        userRequestDto.setPassword(json.getString("password"));
        return userRequestDto;
    }
}
