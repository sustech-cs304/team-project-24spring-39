package com.example.cs304.repository;

import com.example.cs304.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EvaluationRepository extends JpaRepository<Rate, Integer> {
    @Modifying
    @Query(value = "insert into " +
            "rate (course_id, student_id, rate, difficulty, workload, grading, gain, description) " +
            "values (:course_id, :student_id, :rate, :difficulty, :workload, :grading, :gain, :description)", nativeQuery = true)
    public void addEvaluation(String course_id, String student_id, int rate, int difficulty, int workload, int grading, int gain, String description);
}
