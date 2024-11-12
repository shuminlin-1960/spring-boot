package com.start.spring.springboot_tutorial.jwt;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
