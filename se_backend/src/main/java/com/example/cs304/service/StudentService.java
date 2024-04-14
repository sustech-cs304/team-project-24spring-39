package com.example.cs304.service;

import com.example.cs304.entity.Student;
import com.example.cs304.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student validateStudent(Integer sid, String password) {
        Student student = studentRepository.findBySid(sid);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        } else {
            return null;
        }
    }
}