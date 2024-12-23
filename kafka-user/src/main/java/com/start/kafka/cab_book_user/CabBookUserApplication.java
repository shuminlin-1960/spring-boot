package com.start.kafka.cab_book_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CabBookUserApplication {
	public static void main(String[] args) {

		SpringApplication.run(CabBookUserApplication.class, args);
//		This option also works
//		new SpringApplicationBuilder(CabBookUserApplication.class)
//				.web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
//				.run(args);
	}

}
