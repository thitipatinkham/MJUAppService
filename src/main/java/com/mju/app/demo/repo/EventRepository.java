package com.mju.app.demo.repo;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.User;

public interface EventRepository extends CrudRepository<Event,Long>{
//	@Query("from Event e where e.user.approveStatus = 1 and (e.eventDateEnd > now() or e.eventDateEnd between now() and now()) order by e.eventDateStart")
	@Query("from Event e where e.user.approveStatus = 1 and (now() between e.eventDateStart and e.eventDateEnd or now() < e.eventDateEnd) order by e.eventID desc")
	List<Event> getListEvent();
	
	@Query("from Event e where e.user.approveStatus = 1 and (e.eventDateStart between now() and e.eventDateEnd or e.eventDateStart between now() and now()) order by e.eventDateStart")
	List<Event> getEventComing();
	
	@Query("from Event e where e.user.approveStatus = 1 and e.eventID = ?1 and(now() between e.eventDateStart and e.eventDateEnd) order by e.eventDateStart")
	Event selectevent(int eventid);
	
	@Query("from Event e where e.user.approveStatus = 0")
	List<Event> getListEventApprove();
	
	@Query("select e.eventID from Event e where e.eventID = ?1")
	Event checkDup(int eventID);
	
	@Query("Select lp from LocationPlace lp where lp.LocationID = ?1")
	LocationPlace checkDup2(int LocationID);
	
	@Query("from Event e where e.user.email = ?1")
	List<Event> getMyEvents(String email);
	
	@Query("from User u where u.email = ?1")
	User checkDup3(String email);
	
	@Query(value="{call procedure_getEventByID(?1)}", nativeQuery = true)
	Event getEventByID(int eventID);
	
	@Modifying
	@Transactional
	@Query("UPDATE Event e SET e.eventName = ?2 , e.eventDetail = ?3 , e.eventDateStart = ?4 , e.eventDateEnd = ?5 , e.TimeStart = ?6 , e.TimeEnd = ?7 , e.latitude = ?8 , e.longtitude = ?9 ,e.locationplace = ?10 WHERE e.eventID = ?1 ")
	void EditEvent(int eventID ,String eventName , String eventDetail,Date eventDateStart,Date eventDateEnd,Date TimeStart,Date TimeEnd,double latitude,double longtitude,LocationPlace lp);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Event e SET e.eventName = ?2 , e.eventDetail = ?3 , e.eventDateStart = ?4 , e.eventDateEnd = ?5 , e.TimeStart = ?6 , e.TimeEnd = ?7 , e.latitude = ?8 , e.longtitude = ?9 ,e.locationplace = ?10 WHERE e.eventID = ?1 ")
	void EditEvent2(int eventID ,String eventName , String eventDetail,Date eventDateStart,Date eventDateEnd,Date TimeStart,Date TimeEnd,double latitude,double longtitude,LocationPlace location);
	
	@Modifying
	@Transactional
	@Query("UPDATE Event e SET e.eventName = ?2 , e.eventDetail = ?3 , e.eventDateStart = ?4 , e.eventDateEnd = ?5 , e.TimeStart = ?6 , e.TimeEnd = ?7  WHERE e.eventID = ?1 ")
	void EditEvent3(int eventID ,String eventName , String eventDetail,Date eventDateStart,Date eventDateEnd,Date TimeStart,Date TimeEnd);
	
	@Modifying
	@Transactional
	@Query("Delete Event e where e.eventID = ?1 ")
	void DeleteEvent(int eventID);
	
	@Modifying
	@Transactional
	@Query("Delete Comment c where c.event.eventID = ?1 ")
	void DeleteCommentForEvent(int eventID);
	
	@Modifying
	@Transactional
	@Query("Delete Report r where r.event.eventID = ?1 ")
	void DeleteReportForEvent(int eventID);
}
