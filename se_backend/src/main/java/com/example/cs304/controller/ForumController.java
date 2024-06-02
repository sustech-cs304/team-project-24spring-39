package com.example.cs304.controller;

import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.*;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final PostRepository postRepository;
    private final StudentRepository studentRepository;
    private final FileRepository fileRepository;
    private final ReplyRepository replyRepository;
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/get_post_count")
    public Response<?> getPostCount() {
        return new Response<>(200, "Get post count successfully", postRepository.count()/8 +1);
    }
    @GetMapping("/get_department")
    public Response<?> getDepartments(){
        System.out.println(departmentRepository.findAll());
        return Response.success(departmentRepository.findAll());
    }
//    @GetMapping("/get_course_department")
//    public List<Course> getCoursesByDepartment(@RequestParam(required = false) String departmentId) {

//    }
    @GetMapping("/get_post_by_condition")
    public Response<?> getPosts(@RequestParam(required = false) String authorId, @RequestParam(required = false) String majorTag, @RequestParam(required = false) String courseTag) {
        System.out.println("authorId: " + authorId);
        return new Response<>(200, "Get posts successfully", postRepository.findByCondition(authorId, majorTag, courseTag));
    }
//    @GetMapping("/get_post_test")
//    public Response<?> getPostsTest() {
//        return new Response<>(200, "Get posts successfully", postRepository.findAllPostsReplies());
//    }
    @PostMapping("/posting")//暂时用一下，后续要改
    public Post createPost(@RequestHeader String Authorization ,
                           @RequestBody Map<String, Object> requestBody) throws IOException {
        String title = (String) requestBody.get("title");
        String content = (String) requestBody.get("content");
        String majorTag = (String) requestBody.get("majorTag");
        String courseTag = (String) requestBody.get("courseTag");
        MultipartFile multipartFile = (MultipartFile) requestBody.get("file");
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setMajorCategory(majorTag);
        post.setCourseCategory(courseTag);

        Student student = studentRepository.findBySid(jwtTokenProvider.getUsername(Authorization));
        if (student != null) {
            post.setAuthor(student);
        } else {
            throw new IllegalArgumentException("No student found" );
        }

        if (multipartFile != null) {
            File file = new File();
            String name = multipartFile.getOriginalFilename();
            String path = "storage/" +post.getId()+"/"+ name;
            file.setName(name);
            file.setFiletype(multipartFile.getContentType());
            file.setFilepath(path); // replace with your actual storage location
            file.setUploadTime(Instant.now());
            fileRepository.save(file);
            java.io.File fileToSave = new java.io.File(path);
            multipartFile.transferTo(fileToSave);
        }

        return postRepository.save(post);
    }
    //删除帖子
    @DeleteMapping("/delete_post/{postID}")
    public Response<?> deletePost(@PathVariable("postID") int id) {
        try {
            postRepository.deleteById(id);
            return new Response<>(200, "Post deleted successfully", null);
        } catch (EmptyResultDataAccessException e) {
            return new Response<>(404, "Post not found", null);
        }
    }
    //发评论
    @PostMapping("/comment")
    public Response<?> createComment(@RequestHeader String Authorization, @RequestBody Map<String, Object> requestBody) {
        int postId = (int) requestBody.get("postId");
        System.out.println(postId);
        String content = (String) requestBody.get("content");
        System.out.println(content);

        Reply reply = new Reply();
        reply.setContent(content);

        Student student = studentRepository.findBySid(jwtTokenProvider.getUsername(Authorization));
        reply.setAuthor(student);
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            reply.setPost(post);
        } else {
            throw new IllegalArgumentException("No post found with ID: " + postId);
        }

        return new Response<>(200, "Comment created successfully", replyRepository.save(reply));
    }
}