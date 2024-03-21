package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class CourseStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "course_id")
	private Integer courseId;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "score")
	private Integer score;

	public CourseStudent() {}

	public CourseStudent(Integer id, Integer courseId, Integer studentId, Integer score) {
		this.id = id;
		this.courseId = courseId;
		this.studentId = studentId;
		this.score = score;
	}

	@Override
	public String toString() {
		return "CourseStudent{" +
				"id='" + id + '\'' +
				", courseId='" + courseId + '\'' +
				", studentId='" + studentId + '\'' +
				", score='" + score + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
