package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Floor;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.Room;
import com.mju.app.demo.entity.StatusVersion;
import com.mju.app.demo.repo.RoomRepository;
import com.mju.app.demo.util.ResponseObj;
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Room")
public class RoomController {
	@Autowired
	RoomRepository mRoomRepository;
	
	@PostMapping(path="/getListRoom")
	public @ResponseBody ResponseObj getListRoom() {
		List<Room> list = mRoomRepository.getListRoom();
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="AddmoreRoom")
	public @ResponseBody ResponseObj AddmoreRoom(@RequestBody List<Room> roomdata)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {

		List<Room> r = new ArrayList<>();
		for(Room rr : roomdata) {
			System.out.println(rr.toString());
			Room room = new Room();
			room.setRoomName(rr.getRoomName());
			room.setRoomType(rr.getRoomType());
			room.setCapacity(rr.getCapacity());
			room.setHeight(rr.getHeight());
			room.setWidth(rr.getWidth());
			room.setRoomDetail(rr.getRoomDetail());
			LocationPlace lp = locationbyID(rr.getRoomID());
			List<Floor> f = getFloor(lp.getLocationID());
			for(Floor ff : f) {
				System.out.println(ff.getFloorName());
				if(ff.getFloorName().equals(rr.getFloor().getFloorName())) {
					room.setFloor(ff);
				}
			}

			r.add(room);
		}
		mRoomRepository.saveAll(r);
		return new ResponseObj(200, r);
			
	}
	@PostMapping(path="AddRoom")
	public @ResponseBody ResponseObj AddRoom(@RequestBody List<Room> roomdata)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {

		List<Room> r = new ArrayList<>();
		for(Room rr : roomdata) {
			System.out.println(rr.toString());
			Room room = new Room();
			room.setRoomName(rr.getRoomName());
			room.setRoomType(rr.getRoomType());
			room.setCapacity(rr.getCapacity());
			room.setHeight(rr.getHeight());
			room.setWidth(rr.getWidth());
			room.setRoomDetail(rr.getRoomDetail());
			List<LocationPlace> lp = locationplace();
			List<Floor> f = getFloor(lp.get(0).getLocationID());
			for(Floor ff : f) {
				System.out.println(ff.getFloorName());
				if(ff.getFloorName().equals(rr.getFloor().getFloorName())) {
					room.setFloor(ff);
				}
			}

			r.add(room);
		}
		mRoomRepository.saveAll(r);
		return new ResponseObj(200, r);
			
	}
	
	@PostMapping(path="InputmoreRoom")
	public @ResponseBody ResponseObj InputmoreRoom(@RequestBody Room rr)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {

			Room room = new Room();
			room.setRoomName(rr.getRoomName());
			room.setRoomType(rr.getRoomType());
			room.setCapacity(rr.getCapacity());
			room.setHeight(rr.getHeight());
			room.setWidth(rr.getWidth());
			room.setRoomDetail(rr.getRoomDetail());
			Floor f = getFloorByID(rr.getFloor().getFloorID());
			room.setFloor(f);
			System.out.println(room);
		mRoomRepository.save(room);
		StatusVersion s = mRoomRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mRoomRepository.UpdateStatusVersion(""+v);
		return new ResponseObj(200, room);
			
	}
	
	public Floor getFloorByID(int id) {
		return mRoomRepository.getFloorByID(id);
	}
	
	public LocationPlace locationbyID(int locationid) {
		return mRoomRepository.getLocationPlaceByID(locationid);
	}
	
	public List<Floor> getFloor(int id) {
		List<Floor> f = mRoomRepository.getFloor(id);
		return  f ;

	}
	
	public List<LocationPlace> locationplace() {
		if( mRoomRepository.getLocationPlace(new PageRequest(0,1)) != null) {
			List<LocationPlace> lp = mRoomRepository.getLocationPlace(new PageRequest(0,1));
			return  lp ;
		}
		else {
			return null;
		}
		
	}
	
}
