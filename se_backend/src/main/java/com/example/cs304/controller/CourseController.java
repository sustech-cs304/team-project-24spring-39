package com.example.cs304.controller;

import com.example.cs304.entity.Course;
import com.example.cs304.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
    }
}