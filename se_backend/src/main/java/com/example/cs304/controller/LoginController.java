//package com.example.cs304.controller;
//
//import com.example.cs304.entity.Admin;
//import com.example.cs304.entity.Student;
//import com.example.cs304.response.Response;
//import com.example.cs304.service.AdminService;
//import com.example.cs304.service.StudentService;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    private final StudentService studentService;
//
//    private final AdminService adminService;
//
//    public LoginController(StudentService studentService, AdminService adminService) {
//        this.studentService = studentService;
//        this.adminService = adminService;
//    }
//
//       @PostMapping
//    public ResponseEntity<Response<Object>> login(@RequestBody User user) {
//        String id = user.getId();
//        String password = user.getPassword();
//        Response<Object> response = new Response<>();
//        if (id.charAt(0) == '2') {
//            Admin admin = adminService.validateAdmin(id, password);
//            if (admin != null) {
//                response.setStatus(200);
//                response.setMessage("Login successful");
//                response.setData(admin);
//                return ResponseEntity.ok(response);
//            } else {
//                response.setStatus(401);
//                response.setMessage("Invalid id or password");
//                response.setData(null);
//                return ResponseEntity.status(401).body(response);
//            }
//        } else {
//            Student student = studentService.validateStudent(id, password);
//            if (student != null) {
//                response.setStatus(200);
//                response.setMessage("Login successful");
//                response.setData(student);
//                return ResponseEntity.ok(response);
//            } else {
//                response.setStatus(401);
//                response.setMessage("Invalid id or password");
//                response.setData(null);
//                return ResponseEntity.status(401).body(response);
//            }
//        }
//    }
//    }
//class User {
//    @JsonProperty("username")
//    private String id;
//    private String password;
//
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//}