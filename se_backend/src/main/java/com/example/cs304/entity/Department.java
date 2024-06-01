package com.example.cs304.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "department")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Department {
    @Setter
    @Getter
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Getter
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Setter
    @Getter
    @OneToMany(mappedBy = "department")
    private List<Course> courses;

}