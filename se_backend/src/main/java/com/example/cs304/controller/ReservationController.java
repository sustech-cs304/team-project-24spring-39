package com.example.cs304.controller;

import com.example.cs304.entity.Reservation;
import com.example.cs304.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/{student_id}")
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("student_id") String student_id) {
        List<Reservation> reservations = reservationRepository.findBysid(student_id);
        return ResponseEntity.ok(reservations);
    }

//    @GetMapping("/room/{room}")
//    public ResponseEntity<List<Reservation>> getReservationsByRoom(@PathVariable("room") String room) {
//        List<Reservation> reservations = reservationRepository.findByroom(room);
//        return ResponseEntity.ok(reservations);
//    }

    @GetMapping("/rooms")
    public ResponseEntity<List<String>> getRooms() {
        List<String> rooms = reservationRepository.findRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/submit")
    public void addReservation(@RequestParam("student_id") String student_id, @RequestParam("room") String room,
                               @RequestParam("time") String time, @RequestParam("purpose") String purpose) {
        reservationRepository.addReservation(student_id, room, time, purpose);
    }



}
