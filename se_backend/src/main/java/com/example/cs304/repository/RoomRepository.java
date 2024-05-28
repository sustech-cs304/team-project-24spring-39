package com.example.cs304.repository;

import com.example.cs304.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query(value = "select * from room where place = ?;", nativeQuery = true)
    List<Room> findRoomsByBuilding(String building);

    @Override
    Optional<Room> findById(Integer integer);
}
