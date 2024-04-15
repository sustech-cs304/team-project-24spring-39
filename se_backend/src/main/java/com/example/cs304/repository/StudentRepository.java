package com.example.cs304.repository;

import com.example.cs304.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findBySid(String SID);
}
