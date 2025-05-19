package com.app.service.impl;

import com.app.dto.UserDto;
import com.app.entities.User;
import com.app.payload.APIResponse;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public APIResponse<String> register(UserDto dto) {
        APIResponse<String> response = new APIResponse<>();

        if (userRepository.existsByUsername(dto.getUsername())) {
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        response.setMessage("Registration Done");
        response.setStatus(201);
        response.setData("User is registered");
        return response;
    }
}

