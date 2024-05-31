package com.example.cs304.controller;

import com.example.cs304.entity.*;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @PersistenceContext
    private EntityManager entityManager;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final BuildingRepository buildingRepository;
    private final SRrepository SRrepository;

    public ReservationController(ReservationRepository reservationRepository, RoomRepository roomRepository, StudentRepository studentRepository, BuildingRepository buildingRepository, SRrepository SRrepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
        this.buildingRepository = buildingRepository;
        this.SRrepository = SRrepository;
    }

//    @GetMapping("/{student_id}")
//    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("student_id") String student_id) {
//        List<Reservation> reservations = reservationRepository.findBysid(student_id);
//        return ResponseEntity.ok(reservations);
//    }
    @GetMapping("/{student_id}")
    public Response getReservations(@PathVariable("student_id") String student_id) {
        List<Map<String, Object>> students = studentRepository.findnameBysid(student_id);
        return Response.success(students);
    }

//    @GetMapping("/room/{room}")
//    public ResponseEntity<List<Reservation>> getReservationsByRoom(@PathVariable("room") String room) {
//        List<Reservation> reservations = reservationRepository.findByroom(room);
//        return ResponseEntity.ok(reservations);
//    }

    @GetMapping("/locations")
    public ResponseEntity<Response> findLocations() {
        List<Building> buildings = buildingRepository.findAll();
        List<Location> locations = new ArrayList<>();

        for (Building building : buildings) {
            Location location = new Location();
            location.setId(building.getId());
            location.setName(building.getName());
            location.setCapacity(building.getCapacity());
            location.setStatus(building.getStatus());
            List<Room> rooms = roomRepository.findRoomsByBuilding(building.getName());
            location.setRooms(rooms);
            locations.add(location);
        }

        return ResponseEntity.ok(Response.success(locations));
    }

//    @GetMapping("/bookings")
//    public ResponseEntity<List<Reservation>> getReservations() {
//        List<Reservation> reservations = reservationRepository.findAll();
//        return ResponseEntity.ok(reservations);
//    }
    @GetMapping("/bookings/{room_id}")
    public Response getReservations(@PathVariable("room_id") int room_id){
        List<Reservation> reservations = reservationRepository.findByRoom(room_id);
//        List<ReservationRequest> reservations = reservationRepository.findAllReservations();
        List<ReservationRequest> reservationRequests = new ArrayList<>();

        for (Reservation reservation : reservations) {
            ReservationRequest reservationRequest = new ReservationRequest();
            reservationRequest.setId(reservation.getId());
            reservationRequest.setRoom(reservation.getRoom());
            int ReservationID = reservation.getId();
            List<Student> students = studentRepository.findReservationStudent(ReservationID);
            reservationRequest.setStudents(students);
            reservationRequest.setDate(reservation.getDate());
            reservationRequest.setStartTime(reservation.getStartTime());
            reservationRequest.setEndTime(reservation.getEndTime());
            reservationRequest.setCreateTime(reservation.getCreateTime());
            reservationRequest.setStatus(reservation.getStatus());
            reservationRequests.add(reservationRequest);
        }
        return Response.success(reservationRequests);
    }

//    @Modifying
//    @Query(value = "insert into reservation (student_id, room_id, date, start_time, end_time, purpose) values (:student_id, :room_id, :date, :start_time, :end_time, :purpose);", nativeQuery = true)
//    void addReservation(String student_id, int room_id, String date, String start_time, String end_time, String purpose);
    @Transactional
    @PostMapping("/submit")
    public Response addReservation(@RequestParam("persons") List<String> student,
                               @RequestParam("room_id") int room_id,
                               @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               @RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start_time,
                               @RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime end_time
                               ) {
        reservationRepository.addReservation(room_id, date, start_time, end_time);
        int reservation_id = reservationRepository.getLastInsertId();
        for (String student_id : student) {
            SRrepository.insertStudentReservation(student_id, reservation_id);
        }
        return Response.success(null);
    }


}
