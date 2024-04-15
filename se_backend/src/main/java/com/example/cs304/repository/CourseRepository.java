package com.example.cs304.repository;

import com.example.cs304.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT c.name, c.CID, c.type, c.department, c.credit, c.hours, c.capacity, c.selected, c.location, c.time,\n" +
            "       GROUP_CONCAT(JSON_OBJECT('PID', p.PID, 'name', p.name) ORDER BY p.PID SEPARATOR ',') AS professor_list\n" +
            "FROM course c\n" +
            "LEFT JOIN course_professor cp ON c.CID = cp.course_id\n" +
            "LEFT JOIN professor p ON cp.professor_id = p.PID\n" +
            "GROUP BY c.id;", nativeQuery = true)
    List<Map<String,Object>> findAllCoursesProfessors();
}