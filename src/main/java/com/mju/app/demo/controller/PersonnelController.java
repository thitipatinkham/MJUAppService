package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Personnel;
import com.mju.app.demo.entity.Student;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.PersonnelRepository;
import com.mju.app.demo.util.PasswordUtil;
import com.mju.app.demo.util.ResponseObj;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Personnel")
public class PersonnelController {
	private static String SALT = "123456";
	@Autowired
	PersonnelRepository mPersonnelRepository;
	
	@PostMapping(path="/RegisterPersonnel")
	public @ResponseBody ResponseObj RegisterPersonal(@RequestBody Personnel personnel)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(personnel.toString());
		String pSHA = PasswordUtil.getInstance().createPassword( personnel.getUser().getPassword(),SALT);
		mPersonnelRepository.RegisterPersonal(personnel.getUser().getEmail(), personnel.getUser().getEmailLogin(),pSHA);
		Personnel p = new Personnel();
		p.setFullName(personnel.getFullName());
		p.setStaffID(personnel.getStaffID());
		p.setJobPosition(personnel.getJobPosition());
		if(personnel.getBelong().equals("1")) {
			p.setBelong("วิทยาศาสตร์");
		}else {
			p.setBelong(personnel.getBelong());
		}
		User us = mPersonnelRepository.getFBUser(personnel.getUser().getEmail());
		p.setUser(us);
		mPersonnelRepository.save(p);
		User u = mPersonnelRepository.getFBUser(personnel.getUser().getEmail());
		return new ResponseObj(200,u);
	}
}
