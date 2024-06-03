package com.example.cs304.controller;

import com.example.cs304.dto.UserDTO;
import com.example.cs304.entity.Admin;
import com.example.cs304.entity.Student;
import com.example.cs304.repository.StudentRepository;
import com.example.cs304.response.Response;
import com.example.cs304.service.AdminService;
import com.example.cs304.service.AuthService;
import com.example.cs304.service.StudentService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@CrossOrigin
@RestController
public class AuthController {

    private final AuthService authService;
    private final StudentService studentService;
    private final AdminService adminService;
    private final StudentRepository studentRepository;

    // Login REST API
    @PostMapping("/login")
    public Response<?> authenticate(@RequestBody UserDTO userDTO) {
        System.out.println("login");
        String token = authService.login(userDTO);

        if (token.equals("Authentication failed: Invalid username or password.")) {
            return new Response<>(401, "Authentication failed: Invalid username or password.", null);
        }
        Map<String, Object> res;
        if (studentService.getStudentBySID(userDTO.getUsername()) != null){
            res = new HashMap<>(studentService.getStudentBySID(userDTO.getUsername()).convertStudentToMap());
            System.out.println(res);
            res.put("token", token);
        }else {
            res = new HashMap<>();
            res.put("AID", userDTO.getUsername());
            res.put("token", token);
        }
        return new Response<>(200, "Login successful", res);
    }
    @PostMapping("/update_password")
    public Response<?> updatePassword(@RequestBody Map<String, Object> requestBody) {
        String username = (String) requestBody.get("username");
        String oldPassword = (String) requestBody.get("oldPassword");
        String newPassword = (String) requestBody.get("newPassword");
        Student student = studentService.getStudentBySID(username);
        if (student != null){
            if (Objects.equals(student.getPassword(), oldPassword)){
                student.setPassword(newPassword);
                return Response.success(studentRepository.save(student));
            }else {
                return new Response<>(400, "Update password failed: Invalid old password", null);
            }
        }else {
            return new Response<>(400, "Update password failed: Invalid username", null);
        }
    }
}