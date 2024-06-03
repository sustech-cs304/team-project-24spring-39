package com.example.cs304.controller;

import com.example.cs304.dto.UserDTO;
import com.example.cs304.response.Response;
import com.example.cs304.service.AuthService;
import com.example.cs304.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldAuthenticateWhenCredentialsAreValid() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("12345678");
        userDTO.setPassword("password1");

        when(authService.login(userDTO)).thenReturn("validToken");
        when(studentService.getStudentBySID(userDTO.getUsername())).thenReturn(null);

        Response<?> response = authController.authenticate(userDTO);

        assertEquals(200, response.getStatus());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void shouldNotAuthenticateWhenCredentialsAreInvalid() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("invalidUser");
        userDTO.setPassword("invalidPassword");

        when(authService.login(userDTO)).thenReturn("Authentication failed: Invalid username or password.");

        Response<?> response = authController.authenticate(userDTO);

        assertEquals(401, response.getStatus());
        assertEquals("Authentication failed: Invalid username or password.", response.getMessage());
    }

    @Test
    void shouldAuthenticateAsAdminWhenUserDoesNotExistInStudentService() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin1");
        userDTO.setPassword("password1");

        when(authService.login(userDTO)).thenReturn("validToken");
        when(studentService.getStudentBySID(userDTO.getUsername())).thenReturn(null);

        Response<?> response = authController.authenticate(userDTO);

        assertEquals(200, response.getStatus());
        assertEquals("Login successful", response.getMessage());
    }
}