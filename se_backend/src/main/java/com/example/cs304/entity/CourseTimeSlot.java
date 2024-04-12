package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class CourseTimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "course_id")
	private Integer courseId;

	@Column(name = "time_slot_id")
	private Integer timeSlotId;

	public CourseTimeSlot() {}

	public CourseTimeSlot(Integer id, Integer courseId, Integer timeSlotId) {
		this.id = id;
		this.courseId = courseId;
		this.timeSlotId = timeSlotId;
	}

	@Override
	public String toString() {
		return "CourseTimeSlot{" +
				"id='" + id + '\'' +
				", courseId='" + courseId + '\'' +
				", timeSlotId='" + timeSlotId + '\'' +
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

	public Integer getTimeSlotId() {
		return this.timeSlotId;
	}

	public void setTimeSlotId(Integer timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

}
