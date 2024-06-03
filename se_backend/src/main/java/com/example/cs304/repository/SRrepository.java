package com.example.cs304.repository;

import com.example.cs304.entity.StudentReservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SRrepository extends JpaRepository<StudentReservation,Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into student_reservation (student_id, reservation_id) values (?1, ?2);", nativeQuery = true)
    void insertStudentReservation(String student_id, int reservation_id);

    @Modifying
    @Transactional
    @Query(value = "delete from student_reservation where reservation_id = :reservation_id", nativeQuery = true)
    void deleteStudentReservation(@Param("reservation_id") int reservation_id);

}
