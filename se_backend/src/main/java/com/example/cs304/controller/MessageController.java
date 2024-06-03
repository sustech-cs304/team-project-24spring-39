package com.example.cs304.controller;

import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.Message;
import com.example.cs304.repository.MessageRepository;
import com.example.cs304.response.Response;
import com.example.cs304.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
@RequestMapping("message")
@RestController
@CrossOrigin

public class MessageController {
    private final MessageRepository messageRepository;
    private final StudentService studentService;
    private final JwtTokenProvider jwtTokenProvider;

    public MessageController(MessageRepository messageRepository, StudentService studentService, JwtTokenProvider jwtTokenProvider) {
        this.messageRepository = messageRepository;
        this.studentService = studentService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/get_message")
    public Response<?> getMessage(@RequestHeader String Authorization,@RequestParam(required = false) String type) {
        if (jwtTokenProvider.validateToken(Authorization)) {
            String SID = jwtTokenProvider.getUsername(Authorization);
            List<Message> messages = messageRepository.findByCondition(SID, type);
            return new Response<>(200, "Get Successful", messages);
        }else {
            return new Response<>(401, "Invalid Token", null);
        }
    }
    @PostMapping("/add_message")
    public Response<?> addMessage(@RequestHeader String Authorization,@RequestBody Map<String, Object> requestBody) {
        if (!jwtTokenProvider.validateToken(Authorization)) {
            return new Response<>(401, "Invalid Token", null);
        }
        else {
            String content = (String) requestBody.get("content");
            String dateTimeStr = (String) requestBody.get("endTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime endTime = LocalDateTime.parse(dateTimeStr, formatter);
            String receiverId = jwtTokenProvider.getUsername(Authorization);
            messageRepository.addMessage(content, receiverId, endTime, "self");
            return new Response<>(200, "Add Successful", null);
        }
    }
    @DeleteMapping("/delete_message/{mid}")
    public Response<?> deleteMessage(@RequestHeader String Authorization, @PathVariable("mid") int mid) {
        if (!jwtTokenProvider.validateToken(Authorization)) {
            return new Response<>(401, "Invalid Token", null);
        }
        else {
            messageRepository.deleteById(mid);
            return new Response<>(200, "Delete Successful", null);
        }
    }
}
