package com.start.spring.springboot_tutorial.service;

import java.util.List;


public interface CollectionService {
    public int findTotal(List<Student> students);
    public List<Student> sortByAge(List<Student> students);

    List<Student> sortByAgeDecending(List<Student> students);

    List<Student> sortByLastNameDescending(List<Student> students);
}
