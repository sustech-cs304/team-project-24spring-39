package com.example.cs304.controller;

import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.Student;
import com.example.cs304.repository.StudentRepository;
import com.example.cs304.response.Response;
import com.example.cs304.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("/{SID}")
    public Response<?> getStudentCourses(@PathVariable("SID") String SID) {
        List<Map<String, Object>> courses = studentRepository.findCourses(SID);
        return Response.success(courses);
    }

    @GetMapping("/student_info")
    public Response<?> studentInfo(@RequestParam("SID") String SID) {
        List<Map<String, Object>> studentInfo = studentRepository.studentInfo(SID);
        return Response.success(studentInfo);
    }

    @PostMapping("/update_avatar")
    public Response<?> updateAvatar(@RequestHeader String Authorization, @RequestBody String avatar) {
        Student student = studentRepository.findBySid(jwtTokenProvider.getUsername(Authorization));
        student.setAvatar(avatar);
        studentRepository.save(student);
        return Response.success(student);
    }

}