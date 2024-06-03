package com.example.cs304.controller;

import com.example.cs304.controller.StudentController;
import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.Student;
import com.example.cs304.repository.StudentRepository;
import com.example.cs304.response.Response;
import com.example.cs304.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @InjectMocks
    StudentController studentController;

    @Mock
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetStudentCourses() {
        when(studentRepository.findCourses(anyString())).thenReturn(Arrays.asList(Map.of()));
        Response<?> response = studentController.getStudentCourses("SID");
        assertEquals(Response.success(Arrays.asList(Map.of())), response);
    }

    @Test
    void shouldGetStudentInfo() {
        when(studentRepository.studentInfo(anyString())).thenReturn(Arrays.asList(Map.of()));
        Response<?> response = studentController.studentInfo("SID");
        assertEquals(Response.success(Arrays.asList(Map.of())), response);
    }

    @Test
    void shouldUpdateAvatar() {
        Student student = new Student();
        when(jwtTokenProvider.getUsername(anyString())).thenReturn("SID");
        when(studentRepository.findBySid(anyString())).thenReturn(student);
        doNothing().when(studentRepository).save(any(Student.class));
        Response<?> response = studentController.updateAvatar("Authorization", "avatar");
        assertEquals(Response.success(student), response);
    }
}