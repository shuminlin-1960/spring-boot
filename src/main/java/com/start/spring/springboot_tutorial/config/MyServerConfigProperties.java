package com.start.spring.springboot_tutorial.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//Verified working
@Component
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//server.type and server.port are configured in my-resources.properties,
// note the prefix "server" here to be followed by the actual bean attributes, "type" and "port"
@PropertySource("classpath:my-resources.properties")
@ConfigurationProperties(prefix="server")
@Scope("singleton")
public class MyServerConfigProperties {
    private String type;
    private String port;
}
