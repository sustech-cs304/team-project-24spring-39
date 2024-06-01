package com.example.cs304.repository;

import com.example.cs304.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE (:authorId is null or p.author.sid = :authorId) and (:majorTag is null or p.majorCategory = :majorTag) and (:courseTag is null or p.courseCategory = :courseTag)")
List<Post> findByCondition(String authorId, String majorTag, String courseTag);
}