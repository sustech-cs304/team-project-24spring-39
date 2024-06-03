package com.example.cs304.controller;

import com.example.cs304.controller.CourseController;
import com.example.cs304.entity.Course;
import com.example.cs304.repository.CourseRepository;
import com.example.cs304.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void shouldReturnCourseWhenCourseExists() {
        // Given
        Course expectedCourse = new Course();
        when(courseRepository.findById((int) anyLong())).thenReturn(Optional.of(expectedCourse));

        // When
        Response<Course> actualCourse = (Response<Course>) courseController.getCourse(String.valueOf(anyLong()));

        // Then
        assertEquals(expectedCourse, actualCourse.getData());
    }

    @Test
    void shouldReturnNullWhenCourseDoesNotExist() {
        // Given
        when(courseRepository.findById((int) anyLong())).thenReturn(Optional.empty());

        // When
        Response<Course> actualCourse = (Response<Course>) courseController.getCourse(String.valueOf(anyLong()));

        // Then
        assertNull(actualCourse.getData());
    }

    @Test
    void shouldAddCourseWhenAllParametersAreProvided() throws JsonProcessingException {
        // Given
        String name = "Test Course";
        String CID = "TC101";
        String type = "Test Type";
        String department = "Test Department";
        Integer credit = 3;
        Integer hours = 2;
        Integer capacity = 30;
        String location = "Test Location";
        String description = "Test Description";
        List<String> time = Collections.singletonList("10:00");
        List<String> professorNames = Collections.singletonList("Test Professor");

        // When
        Response<?> response = courseController.addCourse(name, CID, type, department, credit, hours, capacity, location, description, time, professorNames);

        // Then
        assertEquals(Response.success(null), response);
    }

    @Test
    void shouldDeleteCourseWhenCourseExists() {
        // Given
        String CID = "TC101";

        // When
        Response<?> response = courseController.deleteCourse(CID);

        // Then
        assertEquals(Response.success(null), response);
    }

    @Test
    void shouldReturnEmptyListWhenCourseTypeDoesNotExist() {
        // Given
        String type = "invalid";
        List<Course> expectedCourses = Collections.emptyList();

        // When
        Response<List<Course>> actualCourses = (Response<List<Course>>) courseController.getCourse(type);

        // Then
        assertEquals(expectedCourses, actualCourses.getData());
    }

}