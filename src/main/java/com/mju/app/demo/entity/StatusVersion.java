package com.mju.app.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="StatusVersion")
public class StatusVersion {
	@Id
	@Column(name="statusVersionName")
	private String statusVersionName;

	public StatusVersion() {
	}

	public StatusVersion(String statusVersionName) {
		super();
		this.statusVersionName = statusVersionName;
	}

	public String getStatusVersionName() {
		return statusVersionName;
	}

	public void setStatusVersionName(String statusVersionName) {
		this.statusVersionName = statusVersionName;
	}

}