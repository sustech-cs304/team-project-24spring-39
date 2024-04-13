package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "SID")
	private Integer sid;

	@Column(name = "password")
	private String password;

	@Column(name = "klass")
	private String klass;

	@Column(name = "major")
	private String major;

	public Student() {}

	public Student(Integer id, String name, Integer sid, String password, String klass, String major) {
		this.id = id;
		this.name = name;
		this.sid = sid;
		this.password = password;
		this.klass = klass;
		this.major = major;
	}

	@Override
	public String toString() {
		return "Students{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sid='" + sid + '\'' +
				", password='" + password + '\'' +
				", klass='" + klass + '\'' +
				", major='" + major + '\'' +
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

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKlass() {
		return this.klass;
	}

	public void setKlass(String klass) {
		this.klass = klass;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

}
