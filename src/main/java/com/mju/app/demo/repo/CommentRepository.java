package com.mju.app.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Comment;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.User;


public interface CommentRepository extends CrudRepository<Comment, Long> {
	@Query("from Comment com where com.event.eventID = ?1")
	List<Comment> getListComment(int eventID);
	
	@Query("from User u where u.email = ?1")
	User checkDup3(String email);
	
	@Query("from Event e where e.eventID = ?1")
	Event checkDup4(int eventID);
	
	@Modifying
	@Transactional
	@Query("Delete Comment c where c.commentID = ?1 ")
	void DeleteMyComment(int commentID);
}
