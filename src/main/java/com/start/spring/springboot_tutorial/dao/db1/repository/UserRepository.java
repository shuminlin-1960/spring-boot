package com.start.spring.springboot_tutorial.dao.db1.repository;

import com.start.spring.springboot_tutorial.dao.db1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
