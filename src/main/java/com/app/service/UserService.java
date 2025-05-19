package com.app.service;

import com.app.dto.UserDto;
import com.app.payload.APIResponse;

public interface UserService {
     APIResponse<String> register(UserDto dto);

}
