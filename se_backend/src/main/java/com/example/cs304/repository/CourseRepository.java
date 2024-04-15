package com.example.cs304.repository;

import com.example.cs304.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT c.id, c.name, c.CID, c.type, c.department, c.credit, c.hours, c.capacity, c.selected, c.location, c.time, p.PID, p.name AS professor_name " +
            "FROM course c " +
            "LEFT JOIN course_professor cp ON c.CID = cp.course_id " +
            "LEFT JOIN professor p ON cp.professor_id = p.PID", nativeQuery = true)
    List<Map<String,Object>> findAllCoursesProfessors();

    // Add course to course_student
    @Modifying
    @Query(value = "insert into course_student(course_id, student_id, score) values (:courseId, :studentId, :score)", nativeQuery = true)
    void selectCourse(@Param("courseId") String courseId, @Param("studentId") String studentId, @Param("score") int score);

}