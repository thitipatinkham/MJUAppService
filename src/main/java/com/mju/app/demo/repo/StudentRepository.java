package com.mju.app.demo.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Student;
import com.mju.app.demo.entity.User;

public interface StudentRepository  extends CrudRepository<Student, Long>{
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.emailLogin = ?2 , u.password = ?3 WHERE u.email = ?1 ")
	void RegisterPersonal(String email ,String emaillogin , String password);
	
	@Query("from User u where u.email = ?1")
	User getFBUser(String email);
	
	
}
