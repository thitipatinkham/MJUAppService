package com.mju.app.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.Event;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;

public interface LocationPlaceRepository extends CrudRepository<LocationPlace, Long> {
	
	@Query("select LocationID from LocationPlace locationplace where locationplace.LocationID = ?1")
	LocationPlace checkDup(int LocationID);
	
	@Query("Select c from Category c where c.CategoryID = ?1")
	Category checkDup2(int CategoryID);
	
	@Query("from LocationPlace locationplace where locationplace.category.CategoryID = ?1")
	List<LocationPlace> getLocationPlace(int CategoryID);
	
	@Query("from LocationPlace locationplace where locationplace.LocationID = ?1")
	LocationPlace getLocationPlaceByID(int LocationID);
	
	@Query("from PlaceDetail placedetail where placedetail.locationPlace.LocationID = ?1")
	List<PlaceDetail> getPlaceByLocationID(int LocationID);

	@Modifying
	@Transactional
	@Query("UPDATE LocationPlace lp SET lp.LocationName = ?2 , lp.LocationDetails = ?3 WHERE lp.LocationID = ?1 ")
	void EditLocationPlace(int LocationID ,String LocationName , String LocationDetails);
	
	@Modifying
	@Transactional
	@Query("Delete LocationPlace lp where lp.LocationID = ?1 ")
	void DeleteLocation(int LocationID);
	
	@Query("from LocationPlace")
	List<LocationPlace> getListLocationPlace();
	
	@Query(value="{call procedure_getLikeLocationPlace(?1)}", nativeQuery = true)
	List<LocationPlace> SearchLocationPlace(String locationName);
	
	@Modifying
	@Transactional
	@Query("UPDATE StatusVersion s SET s.statusVersionName = ?1")
	void UpdateStatusVersion(String statusVersionName);
	
	@Query("from StatusVersion")
	StatusVersion getStatusVersion();
}
