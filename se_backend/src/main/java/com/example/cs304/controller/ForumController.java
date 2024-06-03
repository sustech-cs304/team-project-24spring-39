package com.example.cs304.controller;

import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.entity.*;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/forum")

public class ForumController {

    private final PostRepository postRepository;
    private final StudentRepository studentRepository;
    private final FileRepository fileRepository;
    private final ReplyRepository replyRepository;
    private final DepartmentRepository departmentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${file.storage-path}")
    private String storagePath;
    @Value("${file.server-url}")
    private String serverUrl;

    public ForumController(PostRepository postRepository, StudentRepository studentRepository, FileRepository fileRepository, ReplyRepository replyRepository, MajorRepository majorRepository, DepartmentRepository departmentRepository, JwtTokenProvider jwtTokenProvider) {
        this.postRepository = postRepository;
        this.studentRepository = studentRepository;
        this.fileRepository = fileRepository;
        this.replyRepository = replyRepository;
        this.departmentRepository = departmentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
    @PostMapping(value = "/create_post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public Response<?> createPost(@RequestHeader String Authorization,
                       @RequestPart("file") Optional<MultipartFile> multipartFile,
                       @RequestPart("title") String title,
                       @RequestPart("content") String content,
                       @RequestPart("majorTag") String majorTag,
                       @RequestPart("courseTag") String courseTag) throws IOException {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setMajorCategory(majorTag);
        post.setCourseCategory(courseTag);
        post.setPostingTime(Instant.now());

        Student student = studentRepository.findBySid(jwtTokenProvider.getUsername(Authorization));
        if (student != null) {
            post.setAuthor(student);
        } else {
            throw new IllegalArgumentException("No student found" );
        }
        postRepository.save(post);
//        for(MultipartFile multipartFile: multipartFiles) {
            if (multipartFile.isPresent()) {
                File file = new File();
                String name = multipartFile.get().getOriginalFilename();
                String path = storagePath + post.getId() + name;
                file.setName(name);
                file.setPost(post);
                URI uri = UriComponentsBuilder.fromHttpUrl(serverUrl)
                        .path("/forum/get_file")
                        .queryParam("filePath", path)
                        .build()
                        .toUri();
                file.setFilepath(uri.toString()); // replace with your actual storage location
                file.setUploadTime(Instant.now());
                fileRepository.save(file);
                java.io.File fileToSave = new java.io.File(path);
                multipartFile.get().transferTo(fileToSave);
            }
//        }

        return Response.success(post);
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
        reply.setTime(Instant.now());
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            reply.setPost(post);
        } else {
            throw new IllegalArgumentException("No post found with ID: " + postId);
        }

        return new Response<>(200, "Comment created successfully", replyRepository.save(reply));
    }

    @GetMapping("/get_file")
//    public Response<?> getFile(@RequestParam String filepath) {
//
//    }
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam String filePath) {
         // 替换为文件的实际路径
        java.io.File file = new java.io.File(filePath);
        FileSystemResource resource =  new FileSystemResource(file);
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}