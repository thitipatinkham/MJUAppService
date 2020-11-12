package com.mju.app.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Comment;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.StatusVersion;
import com.mju.app.demo.repo.StatusVersionRepository;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/StatusVersion")
public class StatusVersionController {
	@Autowired
	StatusVersionRepository mStatusVersionRepository;
	
	@PostMapping(path="/getStatusServer")
	public @ResponseBody ResponseObj getStatusServer() {
		StatusVersion list = mStatusVersionRepository.getStatusServer();
		return new ResponseObj(200,list);
	}
}
