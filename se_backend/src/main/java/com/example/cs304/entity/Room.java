package com.example.cs304.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ColumnDefault("空闲")
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}