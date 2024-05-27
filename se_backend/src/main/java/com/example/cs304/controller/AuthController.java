package com.example.cs304.controller;

import com.example.cs304.dto.UserDTO;
import com.example.cs304.response.Response;
import com.example.cs304.service.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthController {

    private AuthService authService;

    // Login REST API
    @PostMapping("/login")
    public Response<?> authenticate(@RequestBody UserDTO userDTO) {
        System.out.println("login");
        String token = authService.login(userDTO);
        if(token.equals("Authentication failed: Invalid username or password.")){
            return new Response<>(401, "Authentication failed: Invalid username or password.", null);
        }
        return new Response<>(200, "Login successful", token);
    }
}