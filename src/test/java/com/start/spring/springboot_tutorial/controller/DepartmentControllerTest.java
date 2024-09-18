package com.start.spring.springboot_tutorial.controller;

import com.start.spring.springboot_tutorial.entity.Department;
import com.start.spring.springboot_tutorial.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService deptService;

    private Department dept;

    @BeforeEach
    void setUp() {
        dept = Department.builder()
                .departmentId(2L)
                .departmentName("ee")
                .departmentAddress("102 ketterer Ct, Lawrenceville, NJ 08648")
                .departmentCode("ce-02")
                .build();
    }

    @Test
    void saveDepartment() throws Exception {
        Department input= Department.builder()
                .departmentName("ee")
                .departmentAddress("102 ketterer Ct, Lawrenceville, NJ 08648")
                .departmentCode("ce-02")
                .build();

        Mockito.when(deptService.saveDepartment(input)).thenReturn(dept);
        mockMvc.perform(MockMvcRequestBuilders.put("/departments")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{" +
                "\"departmentName\": \"ee\"," +
                "\"departmentAddress\": \"102 ketterer Ct, Lawrenceville, NJ 08648\"," +
                "\"departmentCode\": \"ce-02\""
                + "}")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findDepartmentById() throws Exception {
        Mockito.when(deptService.findDepartmentById(2L)).thenReturn(dept);
        mockMvc.perform(get("/departments/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.departmentName").value(dept.getDepartmentName()));

    }
}