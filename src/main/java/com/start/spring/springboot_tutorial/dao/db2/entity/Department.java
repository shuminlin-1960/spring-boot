package com.start.spring.springboot_tutorial.dao.db2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "departmentId", unique = true, nullable = false)
    private long departmentId;

    @NotBlank(message="Please add department name (2 -50 chars)")
    @NotNull
    @Length(max=50, min=2)
    @Column(name = "departmentName", nullable = false, length=50)
    private String departmentName;

    @NotBlank(message="Please add department address (5 -50 chars)")
    @NotNull
    @Length(max=50, min=5)
    @Column(name = "departmentAddress", nullable = false, length=50)
    private String departmentAddress;

    @NotBlank(message="Please add department code (3 -10 chars)")
    @NotNull
    @Length(max=10, min=3)
    @Column(name = "departmentCode", nullable = false, length=50)
    private String departmentCode;
}
