package com.example.cs304.repository;

import com.example.cs304.entity.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into message(content, receiver_id, end_time, type) values (:content, :receiverId, :endTime, :type)", nativeQuery = true)
    void addMessage(String content, String receiverId, LocalDateTime endTime, String type);

    @Query(value = "select * from message where (receiver_id = :SID) and (:type is null or type = :type)", nativeQuery = true)
    List<Message> findByCondition(String SID, String type);
}