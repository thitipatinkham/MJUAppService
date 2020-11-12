package com.mju.app.demo.repo;



import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.Personnel;
import com.mju.app.demo.entity.Student;
import com.mju.app.demo.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	@Query("from User u where u.emailLogin = ?1 and u.password = ?2 and u.statusUser != 3 ")
	User loginUser(String email , String password);
	
	@Query("from User u where u.emailLogin = ?1 and u.statusUser = 3 ")
	User loginUserBaned(String email);
	
	@Query("from User u where u.email = ?1 and u.statusUser != 3 ")
	User loginFBUser(String email);
	
	@Query("from User u where u.email = ?1")
	User getFBUser(String email);
	
	@Query("from User u where u.email = ?1 and u.statusUser = 3 ")
	User loginUserFBBaned(String email);
	
	@Query("from Student s where s.user.email = ?1")
	Student getUserStudent(String email);
	
	@Query("from Personnel p where p.user.email = ?1")
	Personnel getUserPersonnel(String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.approveStatus = ?2 WHERE u.email = ?1 ")
	void ApproveStatus(String email,String approvestatus);
	
	@Query("from User u where u.email = ?1")
	User UserAccount(String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.statusUser = ?1 , u.banDate = ?2 , u.unbanDate = ?3 WHERE u.email = ?4 ")
	void BanUser(int statusUser ,Date banDate , Date unbanDate,String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.statusUser = ?1 , u.banDate = ?2 , u.unbanDate = ?3 WHERE u.email = ?4 ")
	void UnBanUser(int statusUser ,Date banDate , Date unbanDate,String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.emailLogin = ?2 , u.password = ?3 WHERE u.email = ?1 ")
	void RegisterPersonal(String email ,String emaillogin , String password);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = ?2 WHERE u.email = ?1 ")
	void ChangePassword(String email ,String password);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.emailLogin = ?2 WHERE u.email = ?1 ")
	void UpdateUserEmail(String email ,String emailLogin);
	
	@Modifying
	@Transactional
	@Query("UPDATE Student s SET s.faculty = ?2 , s.major = ?3 WHERE s.user.email = ?1 ")
	void UpdateStudent(String email ,String faculty, String major);
	
	@Modifying
	@Transactional
	@Query("UPDATE Personnel p SET p.fullName = ?2 , p.jobPosition = ?3 , p.belong = ?4 WHERE p.user.email = ?1 ")
	void UpdatePersonnel(String email ,String fullName, String jobPosition, String belong);
}
