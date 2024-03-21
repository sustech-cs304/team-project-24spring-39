package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "PID")
	private Integer pid;

	@Column(name = "name")
	private String name;

	@Column(name = "department_id")
	private Integer departmentId;

	public Professor() {}

	public Professor(Integer id, Integer pid, String name, Integer departmentId) {
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "Professor{" +
				"id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", name='" + name + '\'' +
				", departmentId='" + departmentId + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

}
