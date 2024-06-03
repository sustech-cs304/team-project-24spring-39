package com.example.cs304.controller;

import com.example.cs304.controller.ForumController;
import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.Post;
import com.example.cs304.entity.Reply;
import com.example.cs304.entity.Student;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ForumControllerTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private MajorRepository majorRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ForumController forumController;

    private final JwtTokenProvider jwtTokenProvider;

    ForumControllerTest(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPostCount() {
        when(postRepository.count()).thenReturn(8L);

        Response<?> response = forumController.getPostCount();

        assertEquals(200, response.getStatus());
        assertEquals(2, response.getData());
    }

    @Test
    void shouldReturnDepartments() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        Response<?> response = forumController.getDepartments();

        assertEquals(200, response.getStatus());
        assertEquals(Collections.emptyList(), response.getData());
    }

    @Test
    void shouldReturnPostsByCondition() {
        when(postRepository.findByCondition(null, null, null)).thenReturn(Collections.emptyList());

        Response<?> response = forumController.getPosts(null, null, null);

        assertEquals(200, response.getStatus());
        assertEquals(Collections.emptyList(), response.getData());
    }

//    @Test
//    void shouldCreatePost() throws IOException {
//        Post post = new Post();
//        when(postRepository.save(post)).thenReturn(post);
//
//        Post response = forumController.createPost("Authorization", Collections.emptyMap());
//
//        assertEquals(post, response);
//    }

    @Test
    void shouldDeletePost() {
        Response<?> response = forumController.deletePost(1);

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldCreateComment() {
        Reply reply = new Reply();
        when(replyRepository.save(reply)).thenReturn(reply);

        Response<?> response = forumController.createComment("Authorization", Collections.emptyMap());

        assertEquals(200, response.getStatus());
        assertEquals(reply, response.getData());
    }
    // 测试用例：正常情况，成功创建评论


    // 测试用例：学生ID无效，创建评论失败
    @Test
    void testCreateComment_InvalidStudentId_Fail() {
        String Authorization = "validToken";
        int postId = 1;
        String content = "This is a comment.";
        String studentId = "student123";

        when(jwtTokenProvider.getUsername(Authorization)).thenReturn(studentId);
        when(studentRepository.findBySid(studentId)).thenReturn(null);

        Map<String, Object> requestBody = Map.of(
                "postId", postId,
                "content", content
        );

        Response<?> response = forumController.createComment(Authorization, requestBody);

        assertEquals(401, response.getStatus());
        assertEquals("No student found", response.getMessage());
        assertNull(response.getData());
    }

    // 测试用例：评论内容为空，创建评论失败
    @Test
    void testCreateComment_EmptyContent_Fail() {
        String Authorization = "validToken";
        int postId = 1;
        String studentId = "student123";

        when(jwtTokenProvider.getUsername(Authorization)).thenReturn(studentId);
        when(studentRepository.findBySid(studentId)).thenReturn(new Student());

        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        Map<String, Object> requestBody = Map.of(
                "postId", postId,
                "content", "" // 空内容
        );

        Response<?> response = forumController.createComment(Authorization, requestBody);

        assertEquals(400, response.getStatus());
        assertEquals("Content cannot be empty", response.getMessage());
        assertNull(response.getData());
    }
}