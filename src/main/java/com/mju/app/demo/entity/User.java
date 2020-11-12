package com.mju.app.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="User")
public class User {
	@Id
	@Column(name="email")
	private String email;
	@Column(name="emailLogin")
	private String emailLogin;
	@Column(name="password",columnDefinition = "TEXT")
	private String password;
	@Column(name="statusUser")
	private int statusUser;
	@Column(name="approveStatus",columnDefinition = "TEXT")
	private String approveStatus;
	@Column(name="nameAccount",columnDefinition = "TEXT")
	private String nameAccount;
	@Column(name="banDate")
	private Date banDate;
	@Column(name="unbanDate")
	private Date unbanDate;
	
	public User() {
		super();
	}
	

	public String getEmailLogin() {
		return emailLogin;
	}


	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatusUser() {
		return statusUser;
	}
	public void setStatusUser(int statusUser) {
		this.statusUser = statusUser;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getNameAccount() {
		return nameAccount;
	}
	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}
	public Date getBanDate() {
		return banDate;
	}
	public void setBanDate(Date banDate) {
		this.banDate = banDate;
	}
	public Date getUnbanDate() {
		return unbanDate;
	}
	public void setUnbanDate(Date unbanDate) {
		this.unbanDate = unbanDate;
	}


	@Override
	public String toString() {
		return "User [email=" + email + ", emailLogin=" + emailLogin + ", password=" + password + ", statusUser="
				+ statusUser + ", approveStatus=" + approveStatus + ", nameAccount=" + nameAccount + ", banDate="
				+ banDate + ", unbanDate=" + unbanDate + "]";
	}
	
	
	
	
	
}
