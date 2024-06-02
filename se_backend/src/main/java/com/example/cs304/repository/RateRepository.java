package com.example.cs304.repository;

import com.example.cs304.entity.Rate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into rate (course_id, student_id, rate, difficulty, workload, grading, gain, description) " +
            "values (:course_id, :student_id, :rate, :difficulty, :workload, :grading, :gain, :description)", nativeQuery = true)
    void evaluateCourse(String course_id, String student_id, int rate, String difficulty, String workload, String grading, String gain, String description);

    @Query(value = "select * from rate", nativeQuery = true)
    List<Rate> getEvaluations();
}
