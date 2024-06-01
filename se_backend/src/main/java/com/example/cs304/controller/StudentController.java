package com.example.cs304.controller;

import com.example.cs304.repository.StudentRepository;
import com.example.cs304.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{SID}")
    public ResponseEntity<List<Map<String, Object>>> getStudents(@PathVariable("SID") String SID) {
        List<Map<String, Object>> courses = studentRepository.findCourses(SID);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{SID}/info")
    public ResponseEntity<List<Map<String, Object>>> studentInfo(@PathVariable("SID") String SID) {
        List<Map<String, Object>> studentInfo = studentRepository.studentInfo(SID);
        return ResponseEntity.ok(studentInfo);
    }

}