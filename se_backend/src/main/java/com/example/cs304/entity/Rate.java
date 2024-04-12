package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "course_id")
	private Integer courseId;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "rate")
	private Integer rate;

	@Column(name = "difficulty")
	private String difficulty;

	@Column(name = "workload")
	private String workload;

	@Column(name = "grading")
	private String grading;

	@Column(name = "gain")
	private String gain;

	public Rate() {}

	public Rate(Integer id, Integer courseId, Integer studentId, Integer rate, String difficulty, String workload, String grading, String gain) {
		this.id = id;
		this.courseId = courseId;
		this.studentId = studentId;
		this.rate = rate;
		this.difficulty = difficulty;
		this.workload = workload;
		this.grading = grading;
		this.gain = gain;
	}

	@Override
	public String toString() {
		return "Rate{" +
				"id='" + id + '\'' +
				", courseId='" + courseId + '\'' +
				", studentId='" + studentId + '\'' +
				", rate='" + rate + '\'' +
				", difficulty='" + difficulty + '\'' +
				", workload='" + workload + '\'' +
				", grading='" + grading + '\'' +
				", gain='" + gain + '\'' +
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

	public Integer getRate() {
		return this.rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getWorkload() {
		return this.workload;
	}

	public void setWorkload(String workload) {
		this.workload = workload;
	}

	public String getGrading() {
		return this.grading;
	}

	public void setGrading(String grading) {
		this.grading = grading;
	}

	public String getGain() {
		return this.gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

}
