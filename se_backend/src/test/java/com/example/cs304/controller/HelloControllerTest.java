package com.example.cs304.controller;

import com.example.cs304.controller.HelloController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HelloControllerTest {

    private HelloController helloController;

    @BeforeEach
    void setUp() {
        helloController = new HelloController();
    }

    @Test
    void shouldReturnHelloWorld() {
        String result = helloController.hello();
        assertEquals("Hello, world!", result);
    }

    @Test
    void shouldReturnHelloAdmin() {
        String result = helloController.adminHello();
        assertEquals("Hello, admin!", result);
    }

    @Test
    void shouldReturnHelloStudent() {
        String result = helloController.studentHello();
        assertEquals("Hello, student!", result);
    }
}