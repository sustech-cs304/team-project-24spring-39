package com.example.cs304.controller;

import com.example.cs304.entity.Course;
import com.example.cs304.entity.Professor;
import com.example.cs304.repository.CourseRepository;
import com.example.cs304.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;


@PostMapping
public ResponseEntity<Course> addCourse(@RequestBody Course course, @RequestBody Set<Integer> professorIds) {
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
}