package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Student;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.StudentRepository;
import com.mju.app.demo.util.PasswordUtil;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Student")
public class StudentController {
	private static String SALT = "123456";
	@Autowired
	StudentRepository mStudentRepository;
	
	@PostMapping(path="/RegisterStudent")
	public @ResponseBody ResponseObj RegisterPersonal(@RequestBody Student student)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(student.toString());
		String pSHA = PasswordUtil.getInstance().createPassword( student.getUser().getPassword(),SALT);
		mStudentRepository.RegisterPersonal(student.getUser().getEmail(), student.getUser().getEmailLogin(),pSHA);
		Student s = new Student();
		s.setStudentID(student.getStudentID());
		s.setFaculty(student.getFaculty());
		s.setMajor(student.getMajor());
		String a = new String(student.getStudentID());
		String arrB = a.substring(0,2);
		Date d = new Date();
		int y = d.getYear()+1900+543;
		String yy = ""+y;
		String arrB2 = yy.substring(2, 4);
		int x = Integer.parseInt(arrB);	
		int x2 = Integer.parseInt(arrB2);	
		s.setStudentClass((x2-x)+1);
		User us = mStudentRepository.getFBUser(student.getUser().getEmail());
		s.setUser(us);
		mStudentRepository.save(s);
		User u = mStudentRepository.getFBUser(student.getUser().getEmail());
		return new ResponseObj(200,u);
	}
	
	
}
