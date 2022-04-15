package org.yanosik.test.task.common.service.mapper.impl;

import org.json.JSONObject;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;
import org.yanosik.test.task.common.service.mapper.RequestDtoMapper;

public class UserDtoMapper implements RequestDtoMapper<User, UserRequestDto> {

    @Override
    public UserRequestDto modelToRequestDto(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(user.getId());
        userRequestDto.setPassword(user.getPassword());
        return userRequestDto;
    }

    public UserRequestDto jsonToRequestDto(String json) {
        // {d"userIdd":1, "userPassword":"qwerty123"}
        UserRequestDto userRequestDto = new UserRequestDto();
        JSONObject jsonObject = new JSONObject(json);
        userRequestDto.setId((Long) jsonObject.get("userId"));
        userRequestDto.setPassword((String) jsonObject.get("password"));
        return userRequestDto;
    }
}
