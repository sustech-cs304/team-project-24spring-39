package com.example.cs304.service;

import com.example.cs304.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public void selectCourse(String courseId, String studentId, int score) {
        courseRepository.selectCourse(courseId, studentId, score);
    }

    @Transactional
    public void dropCourse(String courseId, String studentId) {
        courseRepository.dropCourse(courseId, studentId);
    }

}

