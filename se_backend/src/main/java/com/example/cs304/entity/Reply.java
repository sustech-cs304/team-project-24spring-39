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
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "content")
	private String content;

	@Column(name = "time")
	private Date time;

	@Column(name = "file_id")
	private Integer fileId;

	public Reply() {}

	public Reply(Integer id, Integer postId, Integer authorId, String content, Date time, Integer fileId) {
		this.id = id;
		this.postId = postId;
		this.authorId = authorId;
		this.content = content;
		this.time = time;
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "Reply{" +
				"id='" + id + '\'' +
				", postId='" + postId + '\'' +
				", authorId='" + authorId + '\'' +
				", content='" + content + '\'' +
				", time='" + time + '\'' +
				", fileId='" + fileId + '\'' +
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}
