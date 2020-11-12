package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.Comment;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.CommentRepository;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Comment")
public class CommentController {
	@Autowired
	CommentRepository mCommentRepository;
	
	@PostMapping(path="/getListComment")
	public @ResponseBody ResponseObj getListComment(@RequestBody Event event) {
		System.out.println("data:"+event.toString());
		List<Comment> list = mCommentRepository.getListComment(event.getEventID());
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="AddComment")
	public @ResponseBody ResponseObj AddComment(@RequestBody Comment com)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		System.out.println(com.toString());
		Comment c = new Comment();
		User u = UserEvent(com.getUser().getEmail()); 
		System.out.println(u.getEmail());
		Event e = getEvent(com.getEvent().getEventID());
		System.out.println(e.getEventName());
		c.setCommentDetail(com.getCommentDetail());
		c.setCommentDateTime(new Date());
		c.setUser(u);
		c.setEvent(e);
		mCommentRepository.save(c);
		return new ResponseObj(200, c);

		
	}
	
	public User UserEvent(String email) {
		if( mCommentRepository.checkDup3(email) != null) {
			User user = mCommentRepository.checkDup3(email);
			return  user ;
		}
		else {
			return null;
		}
		
	}
	public Event getEvent(int eventID) {
		if( mCommentRepository.checkDup4(eventID) != null) {
			Event event = mCommentRepository.checkDup4(eventID);
			return  event ;
		}
		else {
			return null;
		}
		
	}
	
	@PostMapping(path="/DeleteMyComment")
	public @ResponseBody ResponseObj DeleteMyComment(@RequestBody Comment c)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(c.toString());
		int commentID = c.getCommentID();
		mCommentRepository.DeleteMyComment(commentID);
		return new ResponseObj(200,"Success");
	}
}
