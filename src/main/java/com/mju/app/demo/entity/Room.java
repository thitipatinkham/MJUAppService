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
@Table(name="Room")
public class Room {
	@Id
	@SequenceGenerator(name="seq_room", sequenceName="seq_room",initialValue=244)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="seq_room")
	@Column(name="roomID")
	private int roomID;
	
	@Column(name="roomName",columnDefinition = "TEXT")
	private String roomName;
	
	@Column(name="width",columnDefinition = "REAL")
	private double width;
	
	@Column(name="height",columnDefinition = "REAL")
	private double height;
	
	@Column(name="roomType",columnDefinition = "TEXT")
	private String roomType;
	
	@Column(name="capacity")
	private int capacity;
	
	@Column(name="roomDetail",columnDefinition = "TEXT")
	private String roomDetail;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="floorID")
	private Floor floor;

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getRoomDetail() {
		return roomDetail;
	}

	public void setRoomDetail(String roomDetail) {
		this.roomDetail = roomDetail;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", roomName=" + roomName + ", width=" + width + ", height=" + height
				+ ", roomType=" + roomType + ", capacity=" + capacity + ", roomDetail=" + roomDetail + ", floor="
				+ floor + "]";
	}
	
	
	
	
}
