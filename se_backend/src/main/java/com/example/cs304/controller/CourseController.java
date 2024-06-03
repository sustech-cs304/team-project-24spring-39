package com.example.cs304.controller;


import com.example.cs304.entity.*;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import com.example.cs304.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {


    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final RateRepository rateRepository;
    @Autowired
    private final CSrepository csRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CourseService courseService;
    private CPrepository cpRepository;

    public CourseController(CourseRepository courseRepository, ProfessorRepository professorRepository, RateRepository rateRepository, CSrepository csRepository, CPrepository cpRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.rateRepository = rateRepository;
        this.csRepository = csRepository;
        this.cpRepository = cpRepository;
    }

    @PostMapping("/admin_add_course")
    public Response addCourse(@RequestParam("name") String name, @RequestParam("CID") String CID, @RequestParam("semester") String semester,
                              @RequestParam("type") String type, @RequestParam("department") String department, @RequestParam("credit") Integer credit,
                              @RequestParam("hours") Integer hours, @RequestParam("capacity") Integer capacity,
                              @RequestParam("location") String location, @RequestParam("description") String description, @RequestParam("time") List<String> time,
                              @RequestParam("professorIds") List<String> professorIds) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String timeJson = objectMapper.writeValueAsString(time);
        courseRepository.addCourse(name, CID, semester, type, department, credit, hours, capacity, location, description, timeJson);
        String courseId = CID;
        for (String professorId : professorIds) {
            cpRepository.addCourseProfessor(courseId, professorId);
        }
        return Response.success(null);
        //        Set<Professor> professors = professorIds.stream()
//                .map(id -> professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor not found")))
//                .collect(Collectors.toSet());
////        course.setProfessors(professors);
//        for (Professor professor : professors) {
////            professor.getCourses().add(course);
//        }
//        Course savedCourse = courseRepository.save(course);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedCourse.getId()).toUri();
//        return ResponseEntity.created(location).body(savedCourse);
    }

    @DeleteMapping("/admin_delete_course")
    public Response deleteCourse(@RequestParam("CID") String CID) {
        String course_id = CID;
        cpRepository.deleteCourseProfessor(course_id);
        courseRepository.deleteCourse(CID);
        return Response.success(null);
    }


    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
    }


//    @GetMapping("/get_course/all")
//    public ResponseEntity<List<Map<String, Object>>> getCourse() {
//        List<Map<String, Object>> courses = courseRepository.findAllCoursesProfessors();
//        return ResponseEntity.ok(courses);
//    }

    @GetMapping("/get_course/all")
    public ResponseEntity<List<Course>> getCourseTest() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/add_course")
    public ResponseEntity<Response> selectCourse(@RequestParam String course_id, @RequestParam String student_id, @RequestParam int score) {
        try {
            courseRepository.selectCourse(course_id, student_id, score);
            courseRepository.addCourseSelected(course_id);
            return ResponseEntity.ok(Response.success(null));
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Modifying
    @DeleteMapping("/delete_selected_course")
    public ResponseEntity<Response> dropCourse(@RequestParam String course_id, @RequestParam String student_id) {
        try {
            courseService.dropCourse(course_id, student_id);
            return ResponseEntity.ok(Response.success(null));
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //    @GetMapping("/selected_students")
    @GetMapping("/show_selected_students")
    public Response findStudentsInCourse(@RequestParam("CID") String CID) {
        List<Map<String, Object>> students = courseRepository.findStudentsInCourse(CID);
        return Response.success(students);
    }

    @GetMapping("/get_course/bixiu")
    public ResponseEntity<Response> findObligatoryCourses() {
        List<Course> courses = courseRepository.findObligatoryCourses();
        return ResponseEntity.ok(Response.success(courses));
    }

    @GetMapping("/get_course/xuanxiu")
    public ResponseEntity<Response> findElectiveCourses() {
        List<Course> courses = courseRepository.findElectiveCourses();
        return ResponseEntity.ok(Response.success(courses));
    }

    @GetMapping("/show_selected_course")
    public ResponseEntity<Response> findTakenCourses(@RequestParam("SID") String SID) {
        List<CourseStudent> courseStudents = csRepository.findTakenCourses(SID);
        List<SelectedCourse> selectedCourses = new ArrayList<>();
        for (CourseStudent courseStudent : courseStudents) {
            Course course = courseStudent.getCourse();
            SelectedCourse selectedCourse = new SelectedCourse();
            selectedCourse.setId(course.getId());
            selectedCourse.setName(course.getName());
            selectedCourse.setCid(course.getCid());
            selectedCourse.setType(course.getType());
            selectedCourse.setDepartment(course.getDepartment());
            selectedCourse.setCredit(course.getCredit());
            selectedCourse.setSemester(course.getSemester());
            selectedCourse.setHours(course.getHours());
            selectedCourse.setCapacity(course.getCapacity());
            selectedCourse.setSelected(course.getSelected());
            selectedCourse.setLocation(course.getLocation());
            selectedCourse.setDescription(course.getDescription());
            selectedCourse.setProfessors(course.getProfessors());
            selectedCourse.setTime(course.getTime());
            int score = courseStudent.getScore();
            selectedCourse.setScore(score);
            selectedCourses.add(selectedCourse);
        }

        return ResponseEntity.ok(Response.success(selectedCourses));
    }


//    @PostMapping("/evaluation")
//    public Response evaluateCourse(@RequestParam("course_id") String course_id, @RequestParam("student_id") String student_id,
//                                   @RequestParam("rate") int rate, @RequestParam("difficulty") String difficulty,
//                                   @RequestParam("workload") String workload, @RequestParam("grading") String grading,
//                                   @RequestParam("gain") String gain, @RequestParam("description") String description) {
//        System.out.println("test2");
//        rateRepository.evaluateCourse(course_id, student_id, rate, difficulty, workload, grading, gain, description);
//        csRepository.changeJudged(course_id, student_id);
//        return Response.success(null);
//    }

    @GetMapping("/taken")
    public ResponseEntity<Response> findSelectedCourses(@RequestParam("SID") String SID) {
        List<Course> courses = courseRepository.findSelectedCourses(SID);
        return ResponseEntity.ok(Response.success(courses));
    }
}