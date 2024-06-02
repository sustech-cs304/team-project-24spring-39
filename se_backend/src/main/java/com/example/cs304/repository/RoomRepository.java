package com.example.cs304.repository;

import com.example.cs304.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query(value = "select * from room where place = ?;", nativeQuery = true)
    List<Room> findRoomsByBuilding(String building);

    @Override
    Optional<Room> findById(Integer integer);

    @Modifying
    @Transactional
    @Query(value = "insert into room (place, name, capacity, status) values (?1, ?2, ?3,?4);", nativeQuery = true)
    void addRoom(String place, String room_name, int capacity, String status);

    @Modifying
    @Transactional
    @Query(value = "delete from room where id = :room_id", nativeQuery = true)
    void deleteRoom(@Param("room_id") int room_id);

    @Modifying
    @Transactional
    @Query(value = "delete from room where place = :place", nativeQuery = true)
    void deleteRoomByBuilding(@Param("place") String place);
}
