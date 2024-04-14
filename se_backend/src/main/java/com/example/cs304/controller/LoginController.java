package com.example.cs304.controller;

import com.example.cs304.entity.Student;
import com.example.cs304.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final StudentService studentService;

    public LoginController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> login(@RequestBody Student student) {
        Student validStudent = studentService.validateStudent(student.getSid(), student.getPassword());
        if (validStudent != null) {
            return ResponseEntity.ok(validStudent);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}