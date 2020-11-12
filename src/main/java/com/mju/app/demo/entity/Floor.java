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
@Table(name="Floor")
public class Floor {
	@Id
	@SequenceGenerator(name="seq_floor", sequenceName="seq_floor",initialValue=52)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_floor")
	@Column(name="floorID")
	private int floorID;
	
	@Column(name="floorName",columnDefinition = "TEXT")
	private String floorName;
	
	@Column(name="imagePlanPath",columnDefinition = "TEXT")
	private String imagePlanPath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LocationID")
	private LocationPlace locationPlace;

	public int getFloorID() {
		return floorID;
	}

	public void setFloorID(int floorID) {
		this.floorID = floorID;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getImagePlanPath() {
		return imagePlanPath;
	}

	public void setImagePlanPath(String imagePlanPath) {
		this.imagePlanPath = imagePlanPath;
	}

	public LocationPlace getLocationPlace() {
		return locationPlace;
	}

	public void setLocationPlace(LocationPlace locationPlace) {
		this.locationPlace = locationPlace;
	}

	@Override
	public String toString() {
		return "Floor [floorID=" + floorID + ", floorName=" + floorName + ", imagePlanPath=" + imagePlanPath
				+ ", locationPlace=" + locationPlace + "]";
	}
	
	

}
