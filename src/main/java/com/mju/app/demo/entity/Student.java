package com.mju.app.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {
	@Id
	@Column(name = "studentID")
	private String studentID;
	@Column(name = "class")
	private int studentClass;
	@Column(name = "faculty", columnDefinition = "TEXT")
	private String faculty;
	@Column(name = "major", columnDefinition = "TEXT")
	private String major;

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public int getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(int studentClass) {
		this.studentClass = studentClass;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", studentClass=" + studentClass + ", faculty=" + faculty
				+ ", major=" + major + ", user=" + user + "]";
	}
	
	

	
	
}
