package com.example.cs304.controller;

import com.example.cs304.controller.ReservationController;
import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.dto.ReservationRequest;
import com.example.cs304.entity.Reservation;
import com.example.cs304.repository.*;
import com.example.cs304.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @InjectMocks
    ReservationController reservationController;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    BuildingRepository buildingRepository;

    @Mock
    SRrepository sRrepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(new Reservation()));
        Response<?> response = reservationController.getReservations(null, null);
        assertEquals(Response.success(Arrays.asList(new ReservationRequest())), response);
    }

    @Test
    void shouldAddReservation() {
        doNothing().when(reservationRepository).addReservation(anyInt(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class));
        when(reservationRepository.getLastInsertId()).thenReturn(1);
        doNothing().when(sRrepository).insertStudentReservation(anyString(), anyInt());
        Response<?> response = reservationController.addReservation(Arrays.asList("student1"), 1, LocalDate.now(), LocalTime.now(), LocalTime.now());
        assertEquals(Response.success(null), response);
    }

    @Test
    void shouldDeleteReservation() {
        doNothing().when(sRrepository).deleteStudentReservation(anyInt());
        doNothing().when(reservationRepository).deleteById(anyInt());
        Response<?> response = reservationController.deleteReservation(1);
        assertEquals(Response.success(null), response);
    }
}