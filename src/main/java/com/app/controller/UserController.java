package com.app.controller;

import com.app.dto.LoginDto;
import com.app.dto.UserDto;
import com.app.payload.APIResponse;
import com.app.service.UserService;
import com.app.service.impl.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto dto) {
        APIResponse<String> response = userService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }




    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto) {
        APIResponse<String> response = new APIResponse<>();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);
            if (authenticate.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(loginDto.getUsername(),
                        authenticate.getAuthorities().iterator().next().getAuthority());

                response.setMessage("Login Successful");
                response.setStatus(200);
                response.setData(jwtToken);  // return JWT
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

}
