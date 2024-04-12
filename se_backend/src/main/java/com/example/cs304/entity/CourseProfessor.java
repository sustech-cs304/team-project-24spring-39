package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class CourseProfessor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "course_id")
	private Integer courseId;

	@Column(name = "professor_id")
	private Integer professorId;

	public CourseProfessor() {}

	public CourseProfessor(Integer id, Integer courseId, Integer professorId) {
		this.id = id;
		this.courseId = courseId;
		this.professorId = professorId;
	}

	@Override
	public String toString() {
		return "CourseProfessor{" +
				"id='" + id + '\'' +
				", courseId='" + courseId + '\'' +
				", professorId='" + professorId + '\'' +
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

	public Integer getProfessorId() {
		return this.professorId;
	}

	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}

}
