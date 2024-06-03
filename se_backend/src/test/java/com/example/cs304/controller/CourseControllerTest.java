package com.example.cs304.controller;

import com.example.cs304.controller.CourseController;
import com.example.cs304.entity.Course;
import com.example.cs304.repository.CourseRepository;
import com.example.cs304.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }




}