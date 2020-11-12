package com.mju.app.demo.entity;

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
@Table(name="LocationPlace")
public class LocationPlace {
	@Id
	@Column(name="LocationID")
	@SequenceGenerator(name="seq_LP", sequenceName="seq_LP")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_LP")
	private int LocationID;
	
	@Column(name="LocationName",columnDefinition = "TEXT")
	private String LocationName;
	
	@Column(name="LocationDetails",columnDefinition = "TEXT")
	private String LocationDetails;
	
	@Column(name="ImageLocationPath",columnDefinition = "TEXT")
	private String ImageLocationPath;
	
	@Column(name="Latitude",columnDefinition = "REAL")
	private double Latitude;
	
	@Column(name="Longtitude",columnDefinition = "REAL")
	private double Longtitude;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CategoryID")
	private Category category;

	public int getLocationID() {
		return LocationID;
	}

	public void setLocationID(int locationID) {
		LocationID = locationID;
	}

	public String getLocationName() {
		return LocationName;
	}

	public void setLocationName(String locationName) {
		LocationName = locationName;
	}

	public String getLocationDetails() {
		return LocationDetails;
	}

	public void setLocationDetails(String locationDetails) {
		LocationDetails = locationDetails;
	}

	public String getImageLocationPath() {
		return ImageLocationPath;
	}

	public void setImageLocationPath(String imageLocationPath) {
		ImageLocationPath = imageLocationPath;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongtitude() {
		return Longtitude;
	}

	public void setLongtitude(double longtitude) {
		Longtitude = longtitude;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "LocationPlace [LocationID=" + LocationID + ", LocationName=" + LocationName + ", LocationDetails="
				+ LocationDetails + ", ImageLocationPath=" + ImageLocationPath + ", Latitude=" + Latitude
				+ ", Longtitude=" + Longtitude + ", category=" + category + "]";
	}
	
	
	

}
