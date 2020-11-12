package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.Report;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.ReportRepository;
import com.mju.app.demo.util.ResponseObj;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Report")
public class ReportController {
	@Autowired
	ReportRepository mReportRepository;
	
	@PostMapping(path="ReportEvent")
	public @ResponseBody ResponseObj ReportEvent(@RequestBody Report r)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		System.out.println(r.toString());
		Report re = new Report();
		re.setReportDetail(r.getReportDetail());
		re.setReportReason(r.getReportReason());
		re.setReportDate(new Date());
		User u = UserEvent(r.getUser().getEmail()); 
		Event e = getEvent(r.getEvent().getEventID());
		re.setUser(u);
		re.setEvent(e);

		if(mReportRepository.checkDup(re.getReportID()) != null) {
			return new ResponseObj(500, "Dupplicated user!!");
		}else {
			mReportRepository.save(re);
			return new ResponseObj(200, re);
		}
	}
	
	public User UserEvent(String email) {
		if( mReportRepository.checkDup3(email) != null) {
			User user = mReportRepository.checkDup3(email);
			return  user ;
		}
		else {
			return null;
		}
		
	}
	public Event getEvent(int eventID) {
		if( mReportRepository.checkDup4(eventID) != null) {
			Event event = mReportRepository.checkDup4(eventID);
			return  event ;
		}
		else {
			return null;
		}
		
	}
	@PostMapping(path="/getListReport")
	public @ResponseBody ResponseObj getListReport() {
		List<String> list = mReportRepository.getListReport();
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/getUserBaned")
	public @ResponseBody ResponseObj getUserBaned(@RequestBody User user) {
		User users = mReportRepository.checkDup3(user.getEmail());
		System.out.println(user.toString());
		return new ResponseObj(200,users);
	}
	
	@PostMapping(path="/getReportByeventID")
	public @ResponseBody ResponseObj getReportByeventID(@RequestBody Report r) {
		List<String> list = mReportRepository.getReportByeventID(r.getEvent().getEventID());
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/DeleteReport")
	public @ResponseBody ResponseObj DeleteReport(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int eventID = e.getEventID();
		try {
			mReportRepository.DeleteReport(eventID);
			return new ResponseObj(200,"Success");
		}catch(Exception ex) {
			return new ResponseObj(500,"fail");
		}
	
	}
	
}
