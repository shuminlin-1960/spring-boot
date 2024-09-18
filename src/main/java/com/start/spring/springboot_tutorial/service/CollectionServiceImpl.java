package com.start.spring.springboot_tutorial.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Override
    public int findTotal(List<Student> students) {
        return (students == null)? 0 : students.size();
    }

    @Override
    public List<Student> sortByAge(List<Student> students) {
        return students.stream().
                sorted((s1, s2) -> s1.getAge() - s2.getAge())
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortByAgeDecending(List<Student> students) {
        return students.stream()
                .sorted((s1, s2) -> { return (s2.getAge() - s1.getAge()); } )
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortByLastNameDescending(List<Student> students) {
        return students.stream()
                .sorted((s1, s2) -> {
                    return (s2.getLastName().compareTo(s1.getLastName())); } )
                .collect(Collectors.toList());
    }
}
