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
@Table(name="Report")
public class Report {
	@Id
	@Column(name="reportID")
	@SequenceGenerator(name="seq_report", sequenceName="seq_report")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_report")
	private int reportID;
	@Column(name="reportDetail",columnDefinition = "TEXT")
	private String reportDetail;
	@Column(name="reportReason",columnDefinition = "TEXT")
	private String reportReason;
	@Column(name="reportDate")
	private Date reportDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="email")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="eventID")
	private Event event;

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(String reportDetail) {
		this.reportDetail = reportDetail;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

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

	@Override
	public String toString() {
		return "Report [reportID=" + reportID + ", reportDetail=" + reportDetail + ", reportReason=" + reportReason
				+ ", reportDate=" + reportDate + ", user=" + user + ", event=" + event + "]";
	}
	
	
	
	
}
