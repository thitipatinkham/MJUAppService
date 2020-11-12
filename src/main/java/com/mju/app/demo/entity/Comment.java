package com.mju.app.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Comment")
public class Comment {
	@Id
	@Column(name="commentID")
	@SequenceGenerator(name="seq_comment", sequenceName="seq_comment")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_comment")
	private int commentID;
	
	@Column(name="commentDetail",columnDefinition = "TEXT")
	private String commentDetail;
	
	@Column(name="commentDateTime")
	private Date commentDateTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="email")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="eventID")
	private Event event;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}
	
	
	
	
	

	public Date getCommentDateTime() {
		return commentDateTime;
	}

	public void setCommentDateTime(Date commentDateTime) {
		this.commentDateTime = commentDateTime;
	}

	public Comment() {
		super();
	}

	@Override
	public String toString() {
		return "Comment [commentID=" + commentID + ", commentDetail=" + commentDetail + ", commentDateTime="
				+ commentDateTime + ", user=" + user + ", event=" + event + "]";
	}

	
	
}
