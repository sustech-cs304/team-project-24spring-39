package com.example.cs304.dto;

import com.example.cs304.entity.Major;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "student")
public class StudentDTO {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "SID", nullable = false, length = 8)
    private String sid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "major", nullable = false, referencedColumnName = "name")
    private Major major;

    @Column(name = "score", nullable = false)
    private Integer score;
}
