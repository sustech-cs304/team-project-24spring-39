package com.example.cs304.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, referencedColumnName = "CID")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "SID")
    private Student student;

    @ColumnDefault("0")
    @Column(name = "rate", nullable = false)
    private Integer rate;

    @ColumnDefault("normal")
    @Lob
    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @ColumnDefault("normal")
    @Lob
    @Column(name = "workload", nullable = false)
    private String workload;

    @ColumnDefault("normal")
    @Lob
    @Column(name = "grading", nullable = false)
    private String grading;

    @ColumnDefault("normal")
    @Lob
    @Column(name = "gain", nullable = false)
    private String gain;

    @Column(name = "description", length = 1000)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}