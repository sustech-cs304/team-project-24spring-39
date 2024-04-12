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
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "posting_time")
	private Date postingTime;

	@Column(name = "file_id")
	private Integer fileId;

	public Post() {}

	public Post(Integer id, Integer authorId, String title, String content, Date postingTime, Integer fileId) {
		this.id = id;
		this.authorId = authorId;
		this.title = title;
		this.content = content;
		this.postingTime = postingTime;
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "Post{" +
				"id='" + id + '\'' +
				", authorId='" + authorId + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", postingTime='" + postingTime + '\'' +
				", fileId='" + fileId + '\'' +
				'}';
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostingTime() {
		return this.postingTime;
	}

	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}
