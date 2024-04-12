package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class PostCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "category_id")
	private Integer categoryId;

	public PostCategory() {}

	public PostCategory(Integer id, Integer postId, Integer categoryId) {
		this.id = id;
		this.postId = postId;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "PostCategory{" +
				"id='" + id + '\'' +
				", postId='" + postId + '\'' +
				", categoryId='" + categoryId + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostId() {
		return this.postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
