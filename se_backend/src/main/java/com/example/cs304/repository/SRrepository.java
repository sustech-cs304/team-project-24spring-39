package com.example.cs304.repository;

import com.example.cs304.entity.StudentReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SRrepository extends JpaRepository<StudentReservation,Integer> {

//    insert into student_reservation (student_id, reservation_id) values ('12345678', 1);
    @Modifying
    @Query(value = "insert into student_reservation (student_id, reservation_id) values (?1, ?2);", nativeQuery = true)
    void insertStudentReservation(String student_id, int reservation_id);

}
