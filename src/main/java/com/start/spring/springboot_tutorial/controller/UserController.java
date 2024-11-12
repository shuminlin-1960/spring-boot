package com.start.spring.springboot_tutorial.controller;

import jakarta.annotation.sql.DataSourceDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController


@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (userDetailsManager.userExists(username)) {
            return "User " + username + " already exist";
        }

        UserDetails userDetails = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(role.toUpperCase())
                .build();
        userDetailsManager.createUser(userDetails);

        return "User " + username + " created successfully";
    }
}
