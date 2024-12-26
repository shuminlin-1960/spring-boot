package com.start.spring.springboot_tutorial.service.user;

import com.start.spring.springboot_tutorial.dao.db1.entity.User;
import com.start.spring.springboot_tutorial.dao.db1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(String username) {
        return userRepository.findById(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
