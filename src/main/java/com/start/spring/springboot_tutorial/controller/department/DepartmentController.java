package com.start.spring.springboot_tutorial.controller.department;

import com.start.spring.springboot_tutorial.config.MyServerConfigProperties;
import com.start.spring.springboot_tutorial.dao.db2.entity.Department;
import com.start.spring.springboot_tutorial.service.department.DepartmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@PropertySource("classpath:my-resources.properties")
//Works with and w/o global CORS configure --see cors/CorsConfig.java
//@CrossOrigin(origins = "http://127.0.0.1:4200")
public class DepartmentController {
    @Autowired
    private DepartmentService service;


    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

//    Read from YML & properties files
    @Value("${welcome.message}")
    private String welcomeMessage;

//Read from @PropertySource("classpath:my-resources.properties"), verified working
    @Value("${my.key}")
    private String myProperties;

//Works, tested
    @Autowired
    private MyServerConfigProperties configProperties;

    @PostMapping("/departments")
    public Department saveDepartment(@RequestBody Department dept) throws Exception {
        LOGGER.info(welcomeMessage + "saveDepartment called");
        Department returnedDept = service.saveDepartment(dept);
        return returnedDept;
    }


    @GetMapping("/")
    public String home() {
        return "Welcome, home!";
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> fetchDepartments() {
        LOGGER.info("Configured protocol: type=" + configProperties.getType() + ", port=" + configProperties.getPort());
        LOGGER.info("My configure property: " + myProperties);

        LOGGER.info(" fetchDepartments called");
        LOGGER.info("In DepartmentController.fetchDepartments before calling service.fetchDepartments()");
        List<Department>  depts = new ArrayList<>();
        try {
            depts = service.fetchDepartments();
//            LOGGER.info("Depts returned to client: %s", depts != null? JSONUtils.toString(depts) : depts);
//            return depts;

//            This is a working hacking trick to allow CORS accesses
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Access-Control-Allow-Origin", "http://127.0.0.1:4200");
//            headers.add("Access-Control-Allow-Path", "/departments/**");
//            return new ResponseEntity<>(depts, headers, HttpStatus.OK);


            return new ResponseEntity<>(depts, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Error when retrieving departments from the JAP");
//            return depts;
            return new ResponseEntity<>(depts, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/departments/{id}")
    public Department findDepartmentById(@Valid @PathVariable("id") Long deptId) throws Exception {
        LOGGER.info(" findDepartmentById called");
        Department dept =  this.service.findDepartmentById(deptId);
        return dept;
    }


//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/departments/name/{name}")
    public Department findByDepartmentName(@PathVariable("name") String name) {
        LOGGER.info("findByDepartmentName called");
        return this.service.findByDepartmentName(name);
    }

//////////////Return to the client the model of MVC instead of the logical view name///////////////////
    @GetMapping("/departments/dept_name/{name}")
    public ResponseEntity<List<Department>> findDepartmentByName(@PathVariable("name") String name) {
        LOGGER.info("findDepartmentByName called");
        List<Department> departments =  this.service.findDepartmentByNameLessThan(name);
        return ResponseEntity.status(HttpStatus.OK).body(departments);
    }


//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/departments")
    public void deleteAllDepartments()  {
        service.deleteAllDepartments();
        LOGGER.info("deleteAllDepartments called");
    }

//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/departments/{id}")
    public boolean deleteDepartmentById(@Valid @PathVariable("id") Long deptId)  {
        service.deleteDepartmentById(deptId);
        LOGGER.info("deleteDepartmentById called");
        return true;
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartment(@Valid @PathVariable("id") Long deptId, @RequestBody Department dept)  {
        LOGGER.info("updateDepartment called");
        return service.updateDepartment(deptId, dept);
    }


}
