package com.start.spring.springboot_tutorial.service;

import com.start.spring.springboot_tutorial.annotation.CustomerAnnotation;
import com.start.spring.springboot_tutorial.entity.Department;
import com.start.spring.springboot_tutorial.repostitory.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department dept) throws Exception {
        Department deptFound = null;
        try {
            deptFound = departmentRepository.save(dept);
        } catch (Exception e) {
            throw new Exception("Invalid dept input");
        }
        return deptFound;
    }

    @Override
    public List<Department> fetchDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findDepartmentById(long id) throws Exception {
        Optional<Department> dept = departmentRepository.findById(id);
        if (!dept.isPresent()) {
            throw new Exception("Department not available");
        }
        return dept.get();
    }

    @Override
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department updateDepartment(Long deptId, Department dept) {
        Optional<Department> opt_dept = departmentRepository.findById(deptId);
        if (opt_dept.isPresent()) {
            Department old_dept = opt_dept.get();
            old_dept.setDepartmentName(dept.getDepartmentName());
            old_dept.setDepartmentAddress(dept.getDepartmentAddress());
            old_dept.setDepartmentCode(dept.getDepartmentCode());
            return departmentRepository.save(old_dept);
        } else {
            return departmentRepository.save(dept);
        }
    }

    @CustomerAnnotation
    @Override
    public Department findByDepartmentName(String name) {
        return departmentRepository.findByDepartmentName(name);
    }

    @Override
    public List<Department> findDepartmentByNameLessThan(String name) {
        return departmentRepository.findDepartmentByNameLessThan(name);
    }
}
