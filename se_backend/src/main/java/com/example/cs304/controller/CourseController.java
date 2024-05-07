package com.example.cs304.controller;


import com.example.cs304.converter.JsonParser;
import com.example.cs304.entity.Course;
import com.example.cs304.entity.Message;
import com.example.cs304.entity.Professor;
import com.example.cs304.repository.CourseRepository;
import com.example.cs304.repository.ProfessorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {


    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public CourseController(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }


    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course, @RequestBody Set<Integer> professorIds) {//待完善
        Set<Professor> professors = professorIds.stream()
                .map(id -> professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor not found")))
                .collect(Collectors.toSet());
        course.setProfessors(professors);
        for (Professor professor : professors) {
            professor.getCourses().add(course);
        }
        Course savedCourse = courseRepository.save(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCourse.getId()).toUri();
        return ResponseEntity.created(location).body(savedCourse);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
    }


    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getCourses() {
        List<Map<String, Object>> courses = courseRepository.findAllCoursesProfessors();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/test")
    public ResponseEntity<List<Course>> getCoursestest() {
         List<Course> courses = courseRepository.findAllCoursesTest();
        return ResponseEntity.ok(courses);
    }
}