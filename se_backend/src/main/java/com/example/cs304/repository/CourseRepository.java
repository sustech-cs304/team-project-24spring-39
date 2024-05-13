package com.example.cs304.repository;

import com.example.cs304.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer> {

//    @Query

    @Query(value = "SELECT c.id, c.name, c.CID, c.type, c.department, c.credit, c.hours, c.capacity, c.selected, c.location, c.time, p.PID, p.name AS professor_name " +
            "FROM course c " +
            "LEFT JOIN course_professor cp ON c.CID = cp.course_id " +
            "LEFT JOIN professor p ON cp.professor_id = p.PID", nativeQuery = true)
    List<Map<String,Object>> findAllCoursesProfessors();

    // Add course to course_student
    @Modifying
    @Query(value = "insert into course_student(course_id, student_id, score) values (:courseId, :studentId, :score)", nativeQuery = true)
    void selectCourse(@Param("courseId") String courseId, @Param("studentId") String studentId, @Param("score") int score);

    // delete course from course_student
    @Modifying
    @Query(value = "delete from course_student where course_id = :courseId and student_id = :studentId", nativeQuery = true)
    void dropCourse(@Param("courseId") String courseId, @Param("studentId") String studentId);


    @Query(value="select s.sid, s.name\n" +
            "from student s left join course_student cs on s.sid = cs.student_id\n" +
            "where cs.course_id = ?;",nativeQuery = true)
    List<Map<String,Object>> findStudentsInCourse(String CID);

    @Query(value="select * from course where type = '%必修课%';",nativeQuery = true)
    List<Map<String,Object>> findObligatoryCourses();

    @Query(value="select * from course where type = '%选修课%';",nativeQuery = true)
    List<Map<String,Object>> findElectiveCourses();

//    @GetMapping("/{SID}")
//    public ResponseEntity<List<Map<String, Object>>> findTakenCourses(@PathVariable("SID") String SID) {
//        List<Map<String, Object>> courses = courseRepository.findTakenCourses(SID);
//        return ResponseEntity.ok(courses);
//    }
    @Query(value="select c.CID, c.name, c.type, c.department, c.credit, c.hours, c.capacity, c.selected, c.location, c.time, p.PID, p.name AS professor_name\n" +
            "from course c left join course_professor cp on c.CID = cp.course_id\n" +
            "left join professor p on cp.professor_id = p.PID\n" +
            "where c.CID in (select course_id from course_student where student_id = ?);",nativeQuery = true)
    List<Map<String,Object>> findTakenCourses(String SID);
}