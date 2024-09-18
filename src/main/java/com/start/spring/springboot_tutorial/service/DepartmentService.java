package com.start.spring.springboot_tutorial.service;

import com.start.spring.springboot_tutorial.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department saveDepartment(Department dept) throws Exception;

    List<Department> fetchDepartments();

    Department findDepartmentById(long id) throws Exception;

    void deleteAllDepartments();

    void deleteDepartmentById(Long deptId);

    Department updateDepartment(Long deptId, Department dept);

    Department findByDepartmentName(String name);

    List<Department> findDepartmentByNameLessThan(String name);

}
