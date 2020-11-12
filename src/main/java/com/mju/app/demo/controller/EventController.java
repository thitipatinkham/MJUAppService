package com.mju.app.demo.controller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.EventRepository;
import com.mju.app.demo.util.ResponseObj;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Event")
public class EventController {
	@Autowired
	EventRepository mEventRepository;
	
	@PostMapping(path="/getListEvent")
	public @ResponseBody ResponseObj getListEvent() {
		List<Event> list = mEventRepository.getListEvent();
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/getEventComing")
	public @ResponseBody ResponseObj getEventComing(@RequestBody List<Event> event) {
		List<Event> le = new ArrayList();
		for(Event e : event) {	
			System.out.println(e.toString());
			Event ee = mEventRepository.selectevent(e.getEventID());
			if(ee != null) {
				le.add(ee);
			}else {

			}
			
		}
		return new ResponseObj(200,le);
	}
	
	@PostMapping(path="/getListEventApprove")
	public @ResponseBody ResponseObj getListEventApprove() {
		List<Event> list = mEventRepository.getListEventApprove();
		return new ResponseObj(200,list);
	}
	
	
	@PostMapping(path="uploadImageEvent")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/"+file.getOriginalFilename());
		convertFile.createNewFile();
		
		BufferedImage temp = null;
		Image img = ImageIO.read(convertFile);
		temp = resizeImage(img,100,100);
		
		File newFile = convertFile;
		ImageIO.write(temp, "jpg", newFile);
		
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
	}
	
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
	}
	
	@PostMapping(path="addEvent")
	public @ResponseBody ResponseObj addEvent(@RequestBody Event event)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		Event events = new Event();
		System.out.println(event.toString());
		LocationPlace lp = LocationEvent((int) event.getLatitude());
		User u = UserEvent(event.getUser().getEmail()); 
		System.out.println(u.getEmail());
		events.setEventName(event.getEventName());
		events.setEventDetail(event.getEventDetail());
		
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.set(Calendar.YEAR, event.getEventDateStart().getYear()+1900); // set the year
		calendar.set(Calendar.MONTH,event.getEventDateStart().getMonth()); // set the month
		calendar.set(Calendar.DAY_OF_MONTH, event.getEventDateStart().getDate()); // set the day
		calendar.set(Calendar.HOUR_OF_DAY, 1); // set the day
		calendar.set(Calendar.MINUTE, 0); // set the day
		calendar.set(Calendar.SECOND, 0); // set the day
		date.setTime(calendar.getTimeInMillis());
		
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.set(Calendar.YEAR, event.getEventDateEnd().getYear()+1900); // set the year
		calendar2.set(Calendar.MONTH,event.getEventDateEnd().getMonth()); // set the month
		calendar2.set(Calendar.DAY_OF_MONTH, event.getEventDateEnd().getDate()); // set the day
		calendar2.set(Calendar.HOUR_OF_DAY, 23); // set the day
		calendar2.set(Calendar.MINUTE, 59); // set the day
		calendar2.set(Calendar.SECOND, 0); // set the day
		date2.setTime(calendar2.getTimeInMillis());
		
		events.setEventDateStart(date);
		events.setEventDateEnd(date2);
		events.setTimeStart(event.getTimeStart());
		events.setTimeEnd(event.getTimeEnd());
		events.setLatitude(0.0);
		events.setLongtitude(0.0);
		events.setImage(event.getEventName()+event.getImage());
		events.setLocationplace(lp);
		events.setUser(u);

		if(mEventRepository.checkDup(events.getEventID()) != null) {
			return new ResponseObj(500, "Dupplicated user!!");
		}else {
			mEventRepository.save(events);
			return new ResponseObj(200, events);
		}
	}
	
	@PostMapping(path="addEvent2")
	public @ResponseBody ResponseObj addEvent2(@RequestBody Event event)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		Event events = new Event();
		System.out.println(event.getUser().getEmail());
		User u = UserEvent(event.getUser().getEmail()); 
		System.out.println(u.getEmail());
		events.setEventName(event.getEventName());
		events.setEventDetail(event.getEventDetail());
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.set(Calendar.YEAR, event.getEventDateStart().getYear()+1900); // set the year
		calendar.set(Calendar.MONTH,event.getEventDateStart().getMonth()); // set the month
		calendar.set(Calendar.DAY_OF_MONTH, event.getEventDateStart().getDate()); // set the day
		calendar.set(Calendar.HOUR_OF_DAY, 1); // set the day
		calendar.set(Calendar.MINUTE, 0); // set the day
		calendar.set(Calendar.SECOND, 0); // set the day
		date.setTime(calendar.getTimeInMillis());
		
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.set(Calendar.YEAR, event.getEventDateEnd().getYear()+1900); // set the year
		calendar2.set(Calendar.MONTH,event.getEventDateEnd().getMonth()); // set the month
		calendar2.set(Calendar.DAY_OF_MONTH, event.getEventDateEnd().getDate()); // set the day
		calendar2.set(Calendar.HOUR_OF_DAY, 23); // set the day
		calendar2.set(Calendar.MINUTE, 59); // set the day
		calendar2.set(Calendar.SECOND, 0); // set the day
		date2.setTime(calendar2.getTimeInMillis());
		
		events.setEventDateStart(date);
		events.setEventDateEnd(date2);
		events.setTimeStart(event.getTimeStart());
		events.setTimeEnd(event.getTimeEnd());
		events.setLatitude(event.getLatitude());
		events.setLongtitude(event.getLongtitude());
		events.setImage(event.getEventName()+event.getImage());
		events.setLocationplace(null);
		events.setUser(u);

		if(mEventRepository.checkDup(events.getEventID()) != null) {
			return new ResponseObj(500, "Dupplicated user!!");
		}else {
			mEventRepository.save(events);
			return new ResponseObj(200, events);
		}
	}
	
	public LocationPlace LocationEvent(int eventid) {
		if( mEventRepository.checkDup2(eventid) != null) {
			LocationPlace locationplace = mEventRepository.checkDup2(eventid);
			return  locationplace ;
		}
		else {
			return null;
		}
		
	}
	public User UserEvent(String email) {
		if( mEventRepository.checkDup3(email) != null) {
			User user = mEventRepository.checkDup3(email);
			return  user ;
		}
		else {
			return null;
		}
		
	}
	
	@PostMapping(path="/getEventByID")
	public @ResponseBody ResponseObj getEventByID(@RequestBody Event event) {
		System.out.println("data2:"+event.toString());
		Event list = mEventRepository.getEventByID(event.getEventID());
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/DeleteEvent")
	public @ResponseBody ResponseObj DeleteEvent(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int eventID = e.getEventID();
		try {
			Event ev = getevent(eventID);
			FileUtils.forceDelete(new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/"+ev.getImage()));
			mEventRepository.DeleteEvent(eventID);
			return new ResponseObj(200,"Success");
		}catch(Exception ex) {
			return new ResponseObj(500,"fail");
		}
	
	}
	
	public Event getevent(int e) {
		Event ev = mEventRepository.getEventByID(e);
		return ev;
		
		
	}
	
	@PostMapping(path="/DeleteCommentForEvent")
	public @ResponseBody ResponseObj DeleteCommentForEvent(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int eventID = e.getEventID();
		try {
			mEventRepository.DeleteCommentForEvent(eventID);
			return new ResponseObj(200,"Success");
		}catch(Exception ex) {
			return new ResponseObj(500,"fail");
		}
	
	}
	
	@PostMapping(path="/DeleteReportForEvent")
	public @ResponseBody ResponseObj DeleteReportForEvent(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int eventID = e.getEventID();
		try {
			mEventRepository.DeleteReportForEvent(eventID);
			return new ResponseObj(200,"Success");
		}catch(Exception ex) {
			return new ResponseObj(500,"fail");
		}
	
	}
	
	@PostMapping(path="/editEvent")
	public @ResponseBody ResponseObj editEvent(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(e.toString());
		int eventID = e.getEventID();
		String eventName = e.getEventName();
		String eventDetail = e.getEventDetail();
		Date eventDateStart = e.getEventDateStart();
		
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.set(Calendar.YEAR, e.getEventDateEnd().getYear()+1900); // set the year
		calendar2.set(Calendar.MONTH,e.getEventDateEnd().getMonth()); // set the month
		calendar2.set(Calendar.DAY_OF_MONTH, e.getEventDateEnd().getDate()); // set the day
		calendar2.set(Calendar.HOUR_OF_DAY, 23); // set the day
		calendar2.set(Calendar.MINUTE, 59); // set the day
		calendar2.set(Calendar.SECOND, 0); // set the day
		date2.setTime(calendar2.getTimeInMillis());
		
		Date eventDateEnd = date2;
		Date TimeStart = e.getTimeStart();
		Date TimeEnd = e.getTimeEnd();
		double latitude = 0;
		double longtitude = 0;
		LocationPlace lp = LocationEvent((int) e.getLatitude());
		System.out.println(lp.toString());
		mEventRepository.EditEvent(eventID, eventName, eventDetail, eventDateStart, eventDateEnd, TimeStart, TimeEnd, latitude, longtitude,lp);
		Event event = mEventRepository.getEventByID(eventID);
		return new ResponseObj(200,event);
	}
	@PostMapping(path="/editEvent3")
	public @ResponseBody ResponseObj editEvent3(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(e.toString());
		int eventID = e.getEventID();
		String eventName = e.getEventName();
		String eventDetail = e.getEventDetail();
		Date eventDateStart = e.getEventDateStart();
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.set(Calendar.YEAR, e.getEventDateEnd().getYear()+1900); // set the year
		calendar2.set(Calendar.MONTH,e.getEventDateEnd().getMonth()); // set the month
		calendar2.set(Calendar.DAY_OF_MONTH, e.getEventDateEnd().getDate()); // set the day
		calendar2.set(Calendar.HOUR_OF_DAY, 23); // set the day
		calendar2.set(Calendar.MINUTE, 59); // set the day
		calendar2.set(Calendar.SECOND, 0); // set the day
		date2.setTime(calendar2.getTimeInMillis());
		
		Date eventDateEnd = date2;
		Date TimeStart = e.getTimeStart();
		Date TimeEnd = e.getTimeEnd();
		mEventRepository.EditEvent3(eventID, eventName, eventDetail, eventDateStart, eventDateEnd, TimeStart, TimeEnd);
		Event event = mEventRepository.getEventByID(eventID);
		return new ResponseObj(200,event);
	}
	
	@PostMapping(path="/editEvent2")
	public @ResponseBody ResponseObj editEvent2(@RequestBody Event e)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(e.toString());
		int eventID = e.getEventID();
		String eventName = e.getEventName();
		String eventDetail = e.getEventDetail();
		Date eventDateStart = e.getEventDateStart();
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.set(Calendar.YEAR, e.getEventDateEnd().getYear()+1900); // set the year
		calendar2.set(Calendar.MONTH,e.getEventDateEnd().getMonth()); // set the month
		calendar2.set(Calendar.DAY_OF_MONTH, e.getEventDateEnd().getDate()); // set the day
		calendar2.set(Calendar.HOUR_OF_DAY, 23); // set the day
		calendar2.set(Calendar.MINUTE, 59); // set the day
		calendar2.set(Calendar.SECOND, 0); // set the day
		date2.setTime(calendar2.getTimeInMillis());
		
		Date eventDateEnd = date2;
		Date TimeStart = e.getTimeStart();
		Date TimeEnd = e.getTimeEnd();
		double latitude = e.getLatitude();
		double longtitude = e.getLongtitude();
		mEventRepository.EditEvent2(eventID, eventName, eventDetail, eventDateStart, eventDateEnd, TimeStart, TimeEnd, latitude, longtitude,null);
		Event event = mEventRepository.getEventByID(eventID);
		return new ResponseObj(200,event);
	}
	
	@PostMapping(path="/getEventByUser")
	public @ResponseBody ResponseObj getEventByUser(@RequestBody Event event) {
		System.out.println("email:"+event.toString());
		List<Event> list = mEventRepository.getMyEvents(event.getUser().getEmail());
		return new ResponseObj(200,list);
	}
}
