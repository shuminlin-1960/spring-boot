package com.start.spring.springboot_tutorial.repostitory;

import com.start.spring.springboot_tutorial.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

 public Department findByDepartmentName(String name);


 @Query(value = "SELECT * from department where department_name < ?1", nativeQuery = true)
 public List<Department> findDepartmentByNameLessThan(String name);

}

