package com.example.cs304.repository;

import com.example.cs304.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT d FROM Department d JOIN FETCH d.courses")
    List<Department> findAllWithCourses();
}