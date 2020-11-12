package com.mju.app.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Floor;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;

public interface FloorRepository extends CrudRepository<Floor, Long>{
	@Query("from Floor")
	List<Floor> getListFloor();
	
	@Query("from LocationPlace ORDER BY LocationID DESC")
	List<LocationPlace> getLocationPlace(Pageable pageable );
	
	@Query("from LocationPlace where locationID = ?1")
	LocationPlace getLocationPlaceByID(int locationID);
	
	@Modifying
	@Transactional
	@Query("UPDATE StatusVersion s SET s.statusVersionName = ?1")
	void UpdateStatusVersion(String statusVersionName);
	
	@Query("from StatusVersion")
	StatusVersion getStatusVersion();
	
	@Modifying
	@Transactional
	@Query("Delete Floor where floorID = ?1 ")
	void DeleteFloor(int floorID);
}
