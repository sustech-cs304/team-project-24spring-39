package com.example.cs304.repository;

import com.example.cs304.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findBySid(String SID);

    @Query(value="select c.CID, c.name, c.description\n" +
            "from course c\n" +
            "LEFT JOIN course_student cs ON c.CID = cs.course_id\n" +
            "where cs.student_id = ?1 and cs.valid = 1;",nativeQuery = true)
    List<Map<String,Object>> findCourses(String SID);

    @Query(value="select * from student where SID=?;",nativeQuery = true)
    List<Map<String,Object>> studentInfo(String SID);
}
