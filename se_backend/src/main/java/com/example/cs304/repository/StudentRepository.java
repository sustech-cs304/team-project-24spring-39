package com.example.cs304.repository;

import com.example.cs304.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findBySid(String SID);

    @Query(value="select course_id from course_student where student_id = ?1 and valid = 1;",nativeQuery = true)
    List<Map<String,Object>> findCourses(String SID);
}
