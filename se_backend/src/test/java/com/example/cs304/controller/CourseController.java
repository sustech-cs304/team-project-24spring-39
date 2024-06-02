//package com.example.cs304.controller;
//
//
//import com.example.cs304.entity.Course;
//import com.example.cs304.entity.Professor;
//import com.example.cs304.repository.CSrepository;
//import com.example.cs304.repository.CourseRepository;
//import com.example.cs304.repository.ProfessorRepository;
//import com.example.cs304.repository.RateRepository;
//import com.example.cs304.response.Response;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/course")
//public class CourseController {
//
//
//    private final CourseRepository courseRepository;
//    private final ProfessorRepository professorRepository;
//    private final CourseService courseService;
//    private final RateRepository rateRepository;
//    @Autowired
//    private final CSrepository csRepository;
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public CourseController(CourseService courseService, CourseRepository courseRepository, ProfessorRepository professorRepository, RateRepository rateRepository, CSrepository csRepository) {
//        this.courseRepository = courseRepository;
//        this.professorRepository = professorRepository;
//        this.courseService = courseService;
//        this.rateRepository = rateRepository;
//        this.csRepository = csRepository;
//    }
//
//
//    @PostMapping
//    public ResponseEntity<Course> addCourse(@RequestBody Course course, @RequestBody Set<Integer> professorIds) {//待完善
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
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCourse(@PathVariable Integer id) {
//        courseRepository.deleteById(id);
//    }
//
//
//    @GetMapping("/get_course")
//    public ResponseEntity<List<Map<String, Object>>> getCourse() {
//        List<Map<String, Object>> courses = courseRepository.findAllCoursesProfessors();
//        return ResponseEntity.ok(courses);
//    }
//    @GetMapping("/get_course_test")
//    public ResponseEntity<List<Course>> getCourseTest() {
//        List<Course> courses = courseRepository.findAll();
//        return ResponseEntity.ok(courses);
//    }
//
//    @PostMapping("/addCourse")
//    public ResponseEntity<Response> selectCourse(@RequestParam String course_id, @RequestParam String student_id, @RequestParam int score) {
//        try {
//            courseRepository.selectCourse(course_id, student_id, score);
//            return ResponseEntity.ok(Response.success(null));
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//            // Return an appropriate error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DeleteMapping("/deleteSelectedCourse")
//    public ResponseEntity<Void> dropCourse(@RequestParam String course_id, @RequestParam String student_id) {
//        try {
//            courseService.dropCourse(course_id, student_id);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//            // Return an appropriate error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/selectedStudents")
//    public ResponseEntity<List<Map<String, Object>>> findStudentsInCourse(@RequestParam String CID) {
//        try {
//            List<Map<String, Object>> students = courseRepository.findStudentsInCourse(CID);
//            return ResponseEntity.ok().body(students);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/get_course/BiXiu")
//    public ResponseEntity<List<Map<String, Object>>> findObligatoryCourses() {
//        try {
//            List<Map<String, Object>> courses = courseRepository.findObligatoryCourses();
//            return ResponseEntity.ok(courses);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/get_course/XuanXiu")
//    public ResponseEntity<List<Map<String, Object>>> findElectiveCourses() {
//        try {
//            List<Map<String, Object>> courses = courseRepository.findElectiveCourses();
//            return ResponseEntity.ok(courses);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/taken")
//    public ResponseEntity<Response> findTakenCourses(@RequestParam ("SID") String SID) {
//        List<Map<String, Object>> courses = courseRepository.findTakenCourses(SID);
//        return ResponseEntity.ok(Response.success(courses));
//    }
//
//    @Transactional
//    @Rollback(value = false)
//    @PostMapping("/evaluation")
//    public ResponseEntity<Response> evaluateCourse(@RequestParam ("course_id") String course_id, @RequestParam ("student_id") String student_id,
//                                                   @RequestParam ("rate") int rate, @RequestParam ("difficulty") String difficulty,
//                                                   @RequestParam ("workload") String workload,@RequestParam ("grading") String grading,
//                                                   @RequestParam ("gain") String gain, @RequestParam ("description") String description) {
//        try {
//            rateRepository.evaluateCourse(course_id, student_id, rate, difficulty, workload, grading, gain, description);
//            csRepository.changeJudged(course_id, student_id);
////            CourseStudent courseStudent = csRepository.findByCourseIdAndStudentId(course_id, student_id);
////            if (courseStudent != null) {
////                courseStudent.setJudged(true);
////                csRepository.save(courseStudent);
////                entityManager.flush();
////            } else {
////                throw new EntityNotFoundException("CourseStudent not found for courseId: " + course_id + " and studentId: " + student_id);
////            }
//            return ResponseEntity.ok(Response.success(null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}