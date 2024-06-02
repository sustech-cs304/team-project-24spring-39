package com.example.cs304.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "course")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "CID", nullable = false, length = 5)
    private String cid;

    @Column(name = "semester", nullable = false, length = 50)
    private String semester;

    @Lob
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department", nullable = false, referencedColumnName = "name")
    private Department department;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @Getter
    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ColumnDefault("0")
    @Column(name = "selected", nullable = false)
    private Integer selected;

    @Column(name = "location", nullable = false, length = 50)
    private String location;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "time")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> time;

    @ManyToMany
    @JoinTable(
            name = "course_professor",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "CID"),
            inverseJoinColumns = @JoinColumn(name = "professor_id", referencedColumnName = "PID"))
    private List<Professor> professors;

    private Float rate;
}

