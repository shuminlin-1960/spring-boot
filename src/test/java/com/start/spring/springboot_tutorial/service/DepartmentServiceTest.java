package com.start.spring.springboot_tutorial.service;

import com.start.spring.springboot_tutorial.entity.Department;
import com.start.spring.springboot_tutorial.repostitory.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {
    @Autowired
    private DepartmentService service;

    @MockBean
    private DepartmentRepository deptRepository;

    void setUp() {
    }

    @Test
    @DisplayName("=====testFindByDepartmentNameExists=====")
    public void testFindByDepartmentNameExists() {
        Department dept = Department.builder()
                .departmentId(2L)
                .departmentName("ee")
                .departmentAddress("102 1ketterer Ct, Lawrenceville, NJ 08648")
                .departmentCode("ce-02")
                .build();
        Mockito.when(deptRepository.findByDepartmentName("ee")).thenReturn(dept);

        String deptName = "ee";
        Department deptFound = service.findByDepartmentName(deptName);
        assertEquals(deptName, deptFound.getDepartmentName());
    }

//    @Test
//    @DisplayName("=====testFindDepartmentByIdNotExists=====")
//    public void testFindDepartmentByIdNotExists() {
//        Mockito.when(deptRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
//        Department deptFound = service.findDepartmentById(10L);
//        assertThrows(DepartmentNotFoundException.class, ()->deptRepository.findByDepartmentName("az"));
//    }
}