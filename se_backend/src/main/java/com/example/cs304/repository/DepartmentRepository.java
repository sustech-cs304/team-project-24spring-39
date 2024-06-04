package com.example.cs304.repository;

import com.example.cs304.dto.DepartmentDTO;
import com.example.cs304.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
//    @Query(value = "select * ", nativeQuery = true)
//    List<DepartmentDTO> getDepartments();

    @Query(value = "select name from course where department = :department", nativeQuery = true)
    List<String> getCourses(String department);
}