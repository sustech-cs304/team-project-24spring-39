package com.example.cs304.entity;

import jakarta.persistence.*;

/**
 * @author phystar
 * @create 2024-03-22 01:37:35 
 * @description  
 */

@Entity
@SuppressWarnings("all")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "author_id")
	private Integer authorId;

	public Like() {}

	public Like(Integer id, Integer postId, Integer authorId) {
		this.id = id;
		this.postId = postId;
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Like{" +
				"id='" + id + '\'' +
				", postId='" + postId + '\'' +
				", authorId='" + authorId + '\'' +
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

	public Integer getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

}
