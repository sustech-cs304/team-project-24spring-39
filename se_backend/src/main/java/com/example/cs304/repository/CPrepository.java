package com.example.cs304.repository;

import com.example.cs304.entity.CourseProfessor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CPrepository extends JpaRepository<CourseProfessor, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into course_professor(course_id, professor_id) values (:courseId, :professorId)", nativeQuery = true)
    void addCourseProfessor(String courseId, String professorId);

    @Modifying
    @Transactional
    @Query(value = "delete from course_professor where course_id = :course_id", nativeQuery = true)
    void deleteCourseProfessor(@Param("course_id") String course_id);
}
