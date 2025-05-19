package com.app.dto;


import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String name;

    private String username;

    private String email;

    private String password;

    private String role; //This one is added

}
