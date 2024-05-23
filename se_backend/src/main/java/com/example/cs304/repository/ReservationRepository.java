package com.example.cs304.repository;

import com.example.cs304.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value="select * from reservation where student_id=?;",nativeQuery = true)
    List<Reservation> findBysid(String student_id);

    //find by room
    @Query(value = "select * from reservation where room=?;",nativeQuery = true)
    List<Reservation> findByroom(String room);

    @Query(value = "select * from reservation;", nativeQuery = true)
    List<Reservation> findAll();

    @Query(value = "select name, capacity from room;", nativeQuery = true)
    List<String> findRooms();

    @Modifying
    @Query(value = "insert into reservation (student_id, room, time, purpose) values (:student_id, :room, :time, :purpose);", nativeQuery = true)
    public void addReservation(String student_id, String room, String time, String purpose);
}
