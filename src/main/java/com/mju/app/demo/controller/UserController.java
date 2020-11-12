package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.Personnel;
import com.mju.app.demo.entity.Student;
import com.mju.app.demo.entity.User;
import com.mju.app.demo.repo.UserRepository;
import com.mju.app.demo.util.PasswordUtil;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/User")
public class UserController {
	private static String SALT = "123456";
	@Autowired
	UserRepository mUserRepository;

	@PostMapping(path = "/login")
	public @ResponseBody ResponseObj loginUser(@RequestBody User user)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(user.toString());
		String email = user.getEmailLogin();
		String password = user.getPassword();
		String pSHA = PasswordUtil.getInstance().createPassword( user.getPassword(),SALT);
		User usr = mUserRepository.loginUser(email, pSHA);

		if (usr != null) {
			return new ResponseObj(200, usr);
		} else {
			User usrbaned = mUserRepository.loginUserBaned(email);
			if (usrbaned != null) {
				boolean s = usrbaned.getUnbanDate().before(new Date());
				if (s == true) {
					mUserRepository.UnBanUser(1, null, null, email);
					User usr2 = mUserRepository.loginUser(email, password);
					return new ResponseObj(200, usr2);
				} else {
					return new ResponseObj(500, "baned");
				}
			} else {
				return new ResponseObj(500, "fail");
			}
		}
	}

	@PostMapping(path = "/ApproveStatus")
	public @ResponseBody ResponseObj ApproveStatus(@RequestBody User u)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(u.toString());
		mUserRepository.ApproveStatus(u.getEmail(), u.getApproveStatus());
		User user = mUserRepository.UserAccount(u.getEmail());
		return new ResponseObj(200, user);
	}

	@PostMapping(path = "/BanUser")
	public @ResponseBody ResponseObj BanUser(@RequestBody User u)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(u.toString());
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.set(Calendar.YEAR, date.getYear()+1900); // set the year
		calendar.set(Calendar.MONTH,date.getMonth()); // set the month
		calendar.set(Calendar.DAY_OF_MONTH,date.getDate() + u.getStatusUser()); // set the day
		date.setTime(calendar.getTimeInMillis());

		mUserRepository.BanUser(3, new Date(), date, u.getEmail());
		return new ResponseObj(200, "success");
	}
	
	@PostMapping(path = "/UnBanUser")
	public @ResponseBody ResponseObj UnBanUser(@RequestBody User u)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(u.toString());
		mUserRepository.UnBanUser(1, null, null, u.getEmail());
		User usr2 = mUserRepository.getFBUser(u.getEmail());
		return new ResponseObj(200, usr2);
	}

	@PostMapping(path="/loginFBUser")
	public @ResponseBody ResponseObj loginFBUser(@RequestBody User user)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(user.toString());
		String email = user.getEmail();		
	
		User getuser = mUserRepository.getFBUser(email);
		if(getuser == null) {
			User u = new User();
			u.setEmail(user.getEmail());
			u.setApproveStatus("0");
			u.setNameAccount(user.getNameAccount());
			u.setStatusUser(1);
			mUserRepository.save(u);
			return new ResponseObj(200,u);
		}else {
			User usr = mUserRepository.loginFBUser(email);
			if(usr != null) {
				return new ResponseObj(200,usr);
			}else {
				User usrbaned = mUserRepository.loginUserFBBaned(email);
				if(usrbaned != null) {
					boolean s = usrbaned.getUnbanDate().before(new Date());
					if(s == true) {
						mUserRepository.UnBanUser(1, null, null, email);
						User usr2 = mUserRepository.loginFBUser(email);
						usr2.setStatusUser(1);
						return new ResponseObj(200,usr2);
					}else {
						return new ResponseObj(500,"baned");
					}	
				}else {
					return new ResponseObj(500,"fail");
				}
			}
		}
	}
	
	@PostMapping(path="/RegisterPersonal")
	public @ResponseBody ResponseObj RegisterPersonal(@RequestBody User user)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(user.toString());
		String pSHA = PasswordUtil.getInstance().createPassword( user.getPassword(),SALT);
		mUserRepository.RegisterPersonal(user.getEmail(), user.getEmailLogin(),pSHA);
		User u = mUserRepository.getFBUser(user.getEmail());
		return new ResponseObj(200,u);
	}
	
	@PostMapping(path="/getUserProfile")
	public @ResponseBody ResponseObj getUserProfile(@RequestBody User user)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(user.toString());
		User u = mUserRepository.getFBUser(user.getEmail());
		if(u != null) {
			Student s = mUserRepository.getUserStudent(user.getEmail());
			Personnel p = mUserRepository.getUserPersonnel(user.getEmail());
			if(s != null) {
				return new ResponseObj(200,s);
			}else if(p != null) {
				return new ResponseObj(200,p);
			}else {
				return new ResponseObj(200,u);
			}
		}else {
			return new ResponseObj(500,"fail");
		}
	
	}
	
	@PostMapping(path="/ChangePassword")
	public @ResponseBody ResponseObj ChangePassword(@RequestBody User u)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(u.toString());
		String[] s = u.getPassword().split(",");
		String oldpass = PasswordUtil.getInstance().createPassword( s[0],SALT);
		User us = mUserRepository.getFBUser(u.getEmail());
		System.out.println(us.getPassword()+"="+oldpass);
		if(us.getPassword().equals(oldpass)) {
			String pSHA = PasswordUtil.getInstance().createPassword( s[1],SALT);
			mUserRepository.ChangePassword(u.getEmail(),pSHA);
			return new ResponseObj(200,"success");
		}else {
			return new ResponseObj(500,"fail");
		}
	}
	
	@PostMapping(path="/UpdateStudent")
	public @ResponseBody ResponseObj UpdateStudent(@RequestBody Student s)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(s.toString());
		mUserRepository.UpdateUserEmail(s.getUser().getEmail(), s.getUser().getEmailLogin());
		mUserRepository.UpdateStudent(s.getUser().getEmail(), s.getFaculty(), s.getMajor());
		return new ResponseObj(200,"success");
		
	}
	
	@PostMapping(path="/UpdatePersonnel")
	public @ResponseBody ResponseObj UpdatePersonnel(@RequestBody Personnel p)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(p.toString());
		mUserRepository.UpdateUserEmail(p.getUser().getEmail(), p.getUser().getEmailLogin());
		if(p.getBelong().equals("1")) {
			mUserRepository.UpdatePersonnel(p.getUser().getEmail(),p.getFullName(),"วิทยาศาสตร์",p.getBelong());
		}else {
			mUserRepository.UpdatePersonnel(p.getUser().getEmail(),p.getFullName(), p.getBelong(),p.getBelong());
		}
		
		return new ResponseObj(200,"success");
		
	}
}
