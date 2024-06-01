package com.example.cs304.repository;
import com.example.cs304.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}