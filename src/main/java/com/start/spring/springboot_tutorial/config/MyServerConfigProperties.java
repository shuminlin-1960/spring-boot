package com.start.spring.springboot_tutorial.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//Verified working
@Component
@Data
@ConfigurationProperties(prefix="server")
//@NoArgsConstructor
//@AllArgsConstructor
@PropertySource("classpath:my-resources.properties")
@Scope("singleton")
public class MyServerConfigProperties {
    private String type;
    private String port;

//    Replaced by @Data
//    public String getType() {
//        return type;
//    }
//
//    public String getPort() {
//        return port;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
}
