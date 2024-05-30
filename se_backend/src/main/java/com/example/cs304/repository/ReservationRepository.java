package com.example.cs304.repository;

import com.example.cs304.entity.Reservation;
import com.example.cs304.entity.ReservationRequest;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
    @Query(value="select * from reservation where room_id = ?;",nativeQuery = true)
    List<Reservation> findByRoom(int room_id);

    //find by room
//    @Query(value = "select * from reservation where room_id=?;",nativeQuery = true)
//    List<Reservation> findByroom(String room);

    @Query(value = "SELECT\n" +
            "    re.id,\n" +
            "    r.place,\n" +
            "    r.name,\n" +
            "    JSON_ARRAYAGG(JSON_OBJECT('SID', s.SID, 'name', s.name)) AS students,\n" +
            "    re.date,\n" +
            "    re.start_time,\n" +
            "    re.end_time,\n" +
            "    re.create_time,\n" +
            "    re.status\n" +
            "FROM\n" +
            "    reservation re\n" +
            "JOIN room r ON re.room_id = r.id\n" +
            "JOIN student_reservation sr ON re.id = sr.reservation_id\n" +
            "JOIN student s ON sr.student_id = s.SID\n" +
            "GROUP BY\n" +
            "    re.id, r.place, r.name, re.date, re.start_time, re.end_time, re.create_time, re.status;", nativeQuery = true)
    List<ReservationRequest> findAllReservations();

    @Modifying
    @Query(value = "insert into reservation (room_id, date, start_time, end_time) values (:room_id, :date, :start_time, :end_time);", nativeQuery = true)
    void addReservation(int room_id, LocalDate date, LocalTime start_time, LocalTime end_time);

    @Query(value = "select LAST_INSERT_ID();", nativeQuery = true)
    int getLastInsertId();



}




