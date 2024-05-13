package com.example.cs304.controller;
import com.example.cs304.repository.ForumRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {
    private final ForumRepository forumRepository;

    public ForumController(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<Map<String, Object>>> getAllPost() {
        List<Map<String, Object>> posts = forumRepository.getAllPost();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posting")
    public void posting(String author_id, String title, String content, int file_id, String major_category, String course_category) {
        forumRepository.posting(author_id, title, content, file_id, major_category, course_category);
    }

    @PostMapping("/likePost")
    public void like(int post_id, String author_id) {
        forumRepository.like(post_id, author_id);
    }

    @PostMapping("deletePost")
    public void deletePost(int id) {
        forumRepository.deletePost(id);
    }

    @PostMapping("/comment")
    public void comment(int post_id, String author_id, String content, int file_id) {
        forumRepository.comment(post_id, author_id, content, file_id);
    }

}
