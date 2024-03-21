package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:15:40 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Poscategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer posid;

	private Integer categoryId;

	public Poscategory() {}

	public Poscategory(Integer id, Integer posid, Integer categoryId) {
		this.id = id;
		this.posid = posid;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Poscategory{" +
				"id='" + id + '\'' +
				", posid='" + posid + '\'' +
				", categoryId='" + categoryId + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosid() {
		return this.posid;
	}

	public void setPosid(Integer posid) {
		this.posid = posid;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
