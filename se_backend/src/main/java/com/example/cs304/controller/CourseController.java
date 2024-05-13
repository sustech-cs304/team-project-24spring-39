package com.example.cs304.controller;


import com.example.cs304.entity.Course;
import com.example.cs304.entity.Professor;
import com.example.cs304.repository.CourseRepository;
import com.example.cs304.repository.ProfessorRepository;
import com.example.cs304.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {


    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final CourseService courseService;

    public CourseController(CourseService courseService,CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.courseService = courseService;
    }


    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course, @RequestBody Set<Integer> professorIds) {//待完善
        Set<Professor> professors = professorIds.stream()
                .map(id -> professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor not found")))
                .collect(Collectors.toSet());
//        course.setProfessors(professors);
        for (Professor professor : professors) {
//            professor.getCourses().add(course);
        }
        Course savedCourse = courseRepository.save(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCourse.getId()).toUri();
        return ResponseEntity.created(location).body(savedCourse);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
    }


    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getCoursesTest() {
        List<Map<String, Object>> courses = courseRepository.findAllCoursesProfessors();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Void> selectCourse(@RequestParam String course_id, @RequestParam String student_id, @RequestParam int score) {
        try {
            courseService.selectCourse(course_id, student_id, score);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteSelectedCourse")
    public ResponseEntity<Void> dropCourse(@RequestParam String course_id, @RequestParam String student_id) {
        try {
            courseService.dropCourse(course_id, student_id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/selectedStudents")
    public ResponseEntity<List<Map<String, Object>>> findStudentsInCourse(@RequestParam String CID) {
        try {
            List<Map<String, Object>> students = courseRepository.findStudentsInCourse(CID);
            return ResponseEntity.ok().body(students);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getCourse/BiXiu")
    public ResponseEntity<List<Map<String, Object>>> findObligatoryCourses() {
        try {
            List<Map<String, Object>> courses = courseRepository.findObligatoryCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getCourse/XuanXiu")
    public ResponseEntity<List<Map<String, Object>>> findElectiveCourses() {
        try {
            List<Map<String, Object>> courses = courseRepository.findElectiveCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/taken/{SID}")
    public ResponseEntity<List<Map<String, Object>>> findTakenCourses(@PathVariable("SID") String SID) {
        List<Map<String, Object>> courses = courseRepository.findTakenCourses(SID);
        return ResponseEntity.ok(courses);
    }
}