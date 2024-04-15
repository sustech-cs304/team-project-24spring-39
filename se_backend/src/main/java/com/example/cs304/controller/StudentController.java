package com.example.cs304.controller;

import com.example.cs304.repository.StudentRepository;
import com.example.cs304.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }
    @GetMapping
    public Object getStudents() {
        return studentRepository.findAll();
    }
}