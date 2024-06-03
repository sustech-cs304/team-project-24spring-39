package com.example.cs304.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, referencedColumnName = "SID")
    private Student author;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "posting_time", nullable = false)
    private Instant postingTime;

    @Column(name = "major_category", length = 50)
    private String majorCategory;

    @Column(name = "course_category", length = 50)
    private String courseCategory;

}