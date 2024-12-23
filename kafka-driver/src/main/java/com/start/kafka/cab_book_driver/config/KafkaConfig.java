package com.start.kafka.cab_book_driver.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.start.kafka.cab_book_driver.contant.AppConstant.CAB_LOCATION;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(CAB_LOCATION).build();
    }

}
