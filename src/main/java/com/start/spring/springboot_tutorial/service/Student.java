package com.start.spring.springboot_tutorial.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private int age;
}
