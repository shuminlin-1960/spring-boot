package com.start.spring.springboot_tutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue
    private long departmentId;

    @NotBlank(message="Please add department name (4 -50 chars)")
    @NotNull
    @Length(max=10, min=2)
    private String departmentName;

    @NotBlank(message="Please add department address (5 -50 chars)")
    @NotNull
    @Length(max=50, min=5)
    private String departmentAddress;

    @NotBlank(message="Please add department code (5 -50 chars)")
    @NotNull
    @Length(max=10, min=3)
    private String departmentCode;
}
