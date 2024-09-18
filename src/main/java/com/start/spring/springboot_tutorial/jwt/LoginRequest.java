package com.start.spring.springboot_tutorial.jwt;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
