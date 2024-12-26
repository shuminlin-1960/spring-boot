package com.start.spring.springboot_tutorial.repostitory;

import com.start.spring.springboot_tutorial.dao.db2.entity.Department;
import com.start.spring.springboot_tutorial.dao.db2.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository deptRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
//        Department dept = Department.builder()
//                         .departmentId(4l)
//                         .departmentName("de")
//                         .departmentCode("de-04")
//                         .departmentAddress("104 1ketterer Ct, Lawrenceville, NJ 08648")
//                         .build();
//
//        entityManager.persist(dept);
    }


    public void whenFindById_thenReturnDepartment() {
        Department dept = Department.builder()
                .departmentId(4l)
                .departmentName("de")
                .departmentCode("de-04")
                .departmentAddress("104 1ketterer Ct, Lawrenceville, NJ 08648")
                .build();

        entityManager.persist(dept);
        Department deptFound = deptRepository.findById(4L).get();
        assertEquals(deptFound.getDepartmentName(), "de");
    }
}