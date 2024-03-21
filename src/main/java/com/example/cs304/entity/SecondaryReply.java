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
public class SecondaryReply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "reply_id")
	private Integer replyId;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "content")
	private String content;

	@Column(name = "time")
	private Date time;

	@Column(name = "file_id")
	private Integer fileId;

	public SecondaryReply() {}

	public SecondaryReply(Integer id, Integer replyId, Integer authorId, String content, Date time, Integer fileId) {
		this.id = id;
		this.replyId = replyId;
		this.authorId = authorId;
		this.content = content;
		this.time = time;
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "SecondaryReply{" +
				"id='" + id + '\'' +
				", replyId='" + replyId + '\'' +
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

	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
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
