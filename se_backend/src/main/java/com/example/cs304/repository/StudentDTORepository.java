package com.example.cs304.repository;

import com.example.cs304.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentDTORepository extends JpaRepository<StudentDTO, Integer> {
    @Query(value = "select s.id, s.SID, s.name,s.major,cs.score from student s\n" +
            "left join course_student cs on s.SID = cs.student_id\n" +
            "left join course c on c.CID = cs.course_id\n" +
            "where c.CID = :CID", nativeQuery = true)
    List<StudentDTO> findStudentsInCourse(@Param("CID") String CID);
}
