package com.mju.app.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Personnel")
public class Personnel {
	@Id
	@Column(name = "staffID")
	private String staffID;
	@Column(name = "fullName", columnDefinition = "TEXT")
	private String fullName;
	@Column(name = "jobPosition", columnDefinition = "TEXT")
	private String jobPosition;
	@Column(name = "belong", columnDefinition = "TEXT")
	private String belong;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Personnel [staffID=" + staffID + ", fullName=" + fullName + ", jobPosition=" + jobPosition + ", belong="
				+ belong + ", user=" + user + "]";
	}
	
	
	
	
}
