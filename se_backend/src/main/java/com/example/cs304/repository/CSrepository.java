package com.example.cs304.repository;

import com.example.cs304.entity.CourseStudent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CSrepository extends JpaRepository<CourseStudent, Integer> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE course_student SET judged = true " +
            "WHERE course_id = :course_id AND student_id = :student_id", nativeQuery = true)
    void changeJudged(String course_id, String student_id);

    @Query(value="select * from course_student where student_id = ?",nativeQuery = true)
    List<CourseStudent> findTakenCourses(String SID);

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.course.id = :courseId AND cs.student.id = :studentId")
    CourseStudent findByCourseIdAndStudentId(String courseId, String studentId);

}
