package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "department_id")
	private Integer departmentId;

	@Column(name = "credit")
	private Integer credit;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "capacity")
	private Integer capacity;

	public Course() {}

	public Course(Integer id, String name, Integer departmentId, Integer credit, Double rate, Integer capacity) {
		this.id = id;
		this.name = name;
		this.departmentId = departmentId;
		this.credit = credit;
		this.rate = rate;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", departmentId='" + departmentId + '\'' +
				", credit='" + credit + '\'' +
				", rate='" + rate + '\'' +
				", capacity='" + capacity + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getCredit() {
		return this.credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
