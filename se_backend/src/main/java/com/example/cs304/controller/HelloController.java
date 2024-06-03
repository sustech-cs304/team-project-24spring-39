package com.example.cs304.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin

public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello, admin!";
    }
    @GetMapping("/student/hello")
    public String studentHello() {
        return "Hello, student!";
    }
}