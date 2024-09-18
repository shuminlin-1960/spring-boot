package com.start.spring.springboot_tutorial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {
    @GetMapping("/oauth2")
    public ResponseEntity<String> hello() {
//        Also works
//        return ResponseEntity.status(HttpStatus.OK).body("Hello Oauth2!") ;
//        return new ResponseEntity("Hello Oauth2!", HttpStatus.OK) ;
        return ResponseEntity.ok("Hello Oauth2!") ;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
//        Also works
//        return ResponseEntity.status(HttpStatus.OK).body("Hello Oauth2!") ;
//        return new ResponseEntity("Hello Oauth2!", HttpStatus.OK) ;
        return ResponseEntity.ok("Hello login!") ;
    }
}
