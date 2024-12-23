package com.start.spring.springboot_tutorial.controller;

import com.start.spring.springboot_tutorial.jwt.JwtUtils;
import com.start.spring.springboot_tutorial.jwt.LoginRequest;
import com.start.spring.springboot_tutorial.jwt.LoginResponse;
import jakarta.annotation.sql.DataSourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@PreAuthorize("hasRole('ADMIN')")
//@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (userDetailsManager.userExists(username)) {
            return ResponseEntity.ok( "User " + username + " already exist");
        }

        UserDetails userDetails = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(role.toUpperCase())
                .build();
        userDetailsManager.createUser(userDetails);

        return ResponseEntity.ok("User " + username + " created successfully");
    }




    @GetMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password) {

        Authentication authentication = null;
        try {
            authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }  catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String passcode = userDetails.getPassword();
        logger.debug("Roles when the user signed in : {}", userDetails.getAuthorities());
        logger.debug("Password when the user signed in : {}", passcode);

        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);

    }
}
