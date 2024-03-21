package com.example.cs304.entity;

import java.util.Date;
import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "weekday")
	private Integer weekday;

	public TimeSlot() {}

	public TimeSlot(Integer id, Date startTime, Date endTime, Integer weekday) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.weekday = weekday;
	}

	@Override
	public String toString() {
		return "TimeSlot{" +
				"id='" + id + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", weekday='" + weekday + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getWeekday() {
		return this.weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

}
