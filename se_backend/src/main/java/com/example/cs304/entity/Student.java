package com.example.cs304.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "SID", nullable = false, length = 8)
    private String sid;

    @ColumnDefault("000000")
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "class", nullable = false, length = 50)
    private String classField;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "major", nullable = false, referencedColumnName = "name")
    private Major major;

    @ColumnDefault("100")
    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "avatar")
    private byte[] avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

}