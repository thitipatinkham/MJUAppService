package com.mju.app.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.Report;
import com.mju.app.demo.entity.User;

public interface ReportRepository extends CrudRepository<Report, Long> {
	@Query("select r.reportID from Report r where r.reportID = ?1")
	Report checkDup(int reportID);
	
	@Query("from User u where u.email = ?1")
	User checkDup3(String email);
	
	@Query("from Event e where e.eventID = ?1")
	Event checkDup4(int eventID);
	
	@Query(value="{call procedure_getReport}", nativeQuery = true)
	List<String> getListReport();
	
	@Query(value="{call procedure_getReportByeventID(?1)}", nativeQuery = true)
	List<String> getReportByeventID(int eventID);
	
	@Modifying
	@Transactional
	@Query("Delete Report r where r.event.eventID = ?1 ")
	void DeleteReport(int eventID);
	
}
