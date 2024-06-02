package com.example.cs304.repository;

import com.example.cs304.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query(value = "select p from Professor p where (:PID is null or p.pid = :PID)")
    List<Professor> findByCondition(String PID);
}