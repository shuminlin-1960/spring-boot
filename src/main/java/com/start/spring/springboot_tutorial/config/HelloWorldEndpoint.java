package com.start.spring.springboot_tutorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//Not working, single env exclusively
//@Profile(value="dev, prod")
@Profile(value="dev")
@Endpoint(id = "helloWorld")
@Component
public class HelloWorldEndpoint {
    @Value("${welcome.message}")
    private String message;

    @ReadOperation
    public String sayHello(@Selector String name) {

        return  "Hello" + " :" + name + "! " + message;
    }

}
