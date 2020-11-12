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
@Table(name="Event")
public class Event {
	@Id
	@Column(name="eventID")
	@SequenceGenerator(name="seq_event", sequenceName="seq_event")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_event")
	private int eventID;
	@Column(name="eventName",columnDefinition = "TEXT")
	private String eventName;
	@Column(name="eventDateStart")
	private Date eventDateStart;
	@Column(name="eventDateEnd")
	private Date eventDateEnd;
	@Column(name="TimeStart",columnDefinition = "TIME")
	private Date TimeStart;
	@Column(name="TimeEnd",columnDefinition = "TIME")
	private Date TimeEnd;
	@Column(name="eventDetail",columnDefinition = "TEXT")
	private String eventDetail;
	@Column(name="latitude")
	private double latitude;
	@Column(name="longtitude")
	private double longtitude;
	@Column(name="image",columnDefinition = "TEXT")
	private String image;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LocationID")
	private LocationPlace locationplace;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="email")
	private User user;
	

	public Event() {
		super();
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDateStart() {
		return eventDateStart;
	}

	public void setEventDateStart(Date eventDateStart) {
		this.eventDateStart = eventDateStart;
	}

	public Date getEventDateEnd() {
		return eventDateEnd;
	}

	public void setEventDateEnd(Date eventDateEnd) {
		this.eventDateEnd = eventDateEnd;
	}

	public Date getTimeStart() {
		return TimeStart;
	}

	public void setTimeStart(Date timeStart) {
		TimeStart = timeStart;
	}

	public Date getTimeEnd() {
		return TimeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		TimeEnd = timeEnd;
	}

	public String getEventDetail() {
		return eventDetail;
	}

	public void setEventDetail(String eventDetail) {
		this.eventDetail = eventDetail;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocationPlace getLocationplace() {
		return locationplace;
	}

	public void setLocationplace(LocationPlace locationplace) {
		this.locationplace = locationplace;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventName=" + eventName + ", eventDateStart=" + eventDateStart
				+ ", eventDateEnd=" + eventDateEnd + ", TimeStart=" + TimeStart + ", TimeEnd=" + TimeEnd
				+ ", eventDetail=" + eventDetail + ", latitude=" + latitude + ", longtitude=" + longtitude + ", image="
				+ image + ", locationplace=" + locationplace + ", user=" + user + "]";
	}
	


}
