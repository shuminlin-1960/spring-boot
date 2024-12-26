package com.start.spring.springboot_tutorial.service.user;

import com.start.spring.springboot_tutorial.dao.db1.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User saveUser(User user);

    Optional<User> findUser(String username);

    List<User> findAllUsers();
}
