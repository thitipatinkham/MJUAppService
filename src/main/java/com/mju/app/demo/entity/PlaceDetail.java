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
@Table(name="PlaceDetail")
public class PlaceDetail {
	@Id
	@SequenceGenerator(name="seq_pd", sequenceName="seq_pd")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_pd")
	@Column(name="placeDetailID")
	private int placeDetailID;
	
	@Column(name="placeDetailName",columnDefinition = "TEXT")
	private String placeDetailName;
	
	@Column(name="placeDetail_Detail",columnDefinition = "TEXT")
	private String placeDetail_Detail;
	
	@Column(name="placeDetail_Image",columnDefinition = "TEXT")
	private String placeDetail_Image;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LocationID")
	private LocationPlace locationPlace;

	public int getPlaceDetailID() {
		return placeDetailID;
	}

	public void setPlaceDetailID(int placeDetailID) {
		this.placeDetailID = placeDetailID;
	}

	public String getPlaceDetailName() {
		return placeDetailName;
	}

	public void setPlaceDetailName(String placeDetailName) {
		this.placeDetailName = placeDetailName;
	}

	public String getPlaceDetail_Detail() {
		return placeDetail_Detail;
	}

	public void setPlaceDetail_Detail(String placeDetail_Detail) {
		this.placeDetail_Detail = placeDetail_Detail;
	}

	public String getPlaceDetail_Image() {
		return placeDetail_Image;
	}

	public void setPlaceDetail_Image(String placeDetail_Image) {
		this.placeDetail_Image = placeDetail_Image;
	}

	public LocationPlace getLocationPlace() {
		return locationPlace;
	}

	public void setLocationPlace(LocationPlace locationPlace) {
		this.locationPlace = locationPlace;
	}

	@Override
	public String toString() {
		return "PlaceDetail [placeDetailID=" + placeDetailID + ", placeDetailName=" + placeDetailName
				+ ", placeDetail_Detail=" + placeDetail_Detail + ", placeDetail_Image=" + placeDetail_Image
				+ ", locationPlace=" + locationPlace + "]";
	}


	
	
	
	
	
}
