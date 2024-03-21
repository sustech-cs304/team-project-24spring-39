package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "capacity")
	private Integer capacity;

	public Room() {}

	public Room(Integer id, String name, Integer capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Room{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
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

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
