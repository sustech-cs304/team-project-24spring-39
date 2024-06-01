package com.example.cs304.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
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
    private String avatar;

    public Map<String, Object> convertStudentToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("SID", getSid());
        map.put("class", getClassField());
        map.put("major", getMajor().getName());
        map.put("score", getScore());
        map.put("avatar", getAvatar());
        return map;
    }
}