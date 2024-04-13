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

    public boolean validateStudent(Integer SID, String password) {
        Student student = studentRepository.findById(SID).orElse(null);
        if (student != null) {
            return student.getPassword().equals(password);
        }
        return false;
    }
}