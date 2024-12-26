package com.start.spring.springboot_tutorial.dao.db1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Entity
@Table(schema="user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "username", unique = true, nullable = false, length=50)
    private String username;

    @Column(name = "password", nullable = false, length=500)
    private  String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;


    @OneToMany(mappedBy = "user")
    Set<Authority> authorities;

}
