package com.start.spring.springboot_tutorial.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollectionServiceTest {
    @Autowired
    CollectionService service;
    List<Student> students = new ArrayList<>();

    @BeforeEach
    public void setup() {
        Student student1 = Student.builder()
                .firstName("F1")
                .lastName("L1")
                .age(11).build();

        Student student2 = Student.builder()
                .firstName("F2")
                .lastName("L2")
                .age(22).build();

        Student student3 = Student.builder()
                .firstName("F3")
                .lastName("L3")
                .age(33).build();

        Student student4 = Student.builder()
                .firstName("F3")
                .lastName("L4")
                .age(44).build();

        Student student5 = Student.builder()
                .firstName("F3")
                .lastName("L5")
                .age(55).build();


//Option 1
// students = List.of(student1,student2, student3);

//Option 2
//        Student studentsArray[] = new Student[] { student1, student2, student3};
//        students = Arrays.asList(studentArray);

//Option 3
        Collections.addAll(students, student1, student4, student3, student5, student2);
        System.out.println("Original order: " + students);

        Collections.reverse(students);
        System.out.println("Reversed order: " + students);

        List<Student> oddAged = students.stream().filter(s -> s.getAge() % 2 == 0).toList();
        System.out.println("Even ages: " + oddAged);

        List<Integer> doubledAges = students.stream().map(s -> s.getAge()*2).toList();
        System.out.println("Doubled ages: " + doubledAges);

        doubledAges = students.stream().map(s -> s.getAge()*2).limit(2).toList();
        System.out.println("First two doubled ages: " + doubledAges);


        List<Student> ageDoubled = new ArrayList<>();

        students.stream().forEach(s -> {
            ageDoubled.add(Student.builder()
                        .firstName(s.getFirstName())
                        .lastName(s.getLastName())
                        .age(s.getAge()*2)
                        .build());});
        System.out.println("Doubled-ageed students: " + ageDoubled);



    }

    @AfterEach
    public void tearDown() {
        students = null;
    }

    @Test
    public void testFindTotal() {
        int total = service.findTotal(students);
        System.out.println("Total count: " + total);
        assertEquals(total, 5);
    }


    @Test
    public void testSortedByNaturalOrder()
    {
        List<Student>  sorted = service.sortByAge(students);
        assertEquals(sorted.get(0).getAge(), 11);
    }


    @Test
    public void testSortedByReverseNaturalOrder()
    {
        List<Student>  sorted = service.sortByAgeDecending(students);
        assertEquals(sorted.get(0).getAge(), 55);
    }



    @Test
    public void testSortedByLastNameDescending()
    {
        System.out.println("unsorted: " + students);
        List<Student>  sorted = service.sortByLastNameDescending(students);
        System.out.println("Sorted: " + sorted);

        List<String>  loweredCases = new ArrayList<>();
        loweredCases = sorted.stream().map(n ->
                n.getFirstName().toLowerCase()
                ).toList();

        System.out.println("Lowercased: " + loweredCases);



        assertEquals(sorted.get(0).getLastName(), "L5");
    }
}