package com.mju.app.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;

public interface PlaceDetailRepository extends CrudRepository<PlaceDetail, Long>  {
	@Query("select placeDetailID from PlaceDetail placedetail where placedetail.placeDetailID = ?1")
	PlaceDetail checkDup(int placeDetailID);
	
	@Query("from LocationPlace ORDER BY LocationID DESC")
	List<LocationPlace> getLocationPlace(Pageable pageable );
	
	@Query("from PlaceDetail placedetail where placedetail.locationPlace.LocationID = ?1")
	List<PlaceDetail> getPlaceByLocationID(int LocationID);
	
	@Query("from PlaceDetail placedetail where placedetail.placeDetailID = ?1")
	PlaceDetail getPlaceDetailByID(int placeDetailID);
	
	@Query("from PlaceDetail")
	List<PlaceDetail> getListPlaceDetail();
	
	@Query("from LocationPlace where locationID = ?1")
	LocationPlace getLocationPlaceByID(int locationID);
	
	@Modifying
	@Transactional
	@Query("UPDATE PlaceDetail p SET p.placeDetailName = ?2 , p.placeDetail_Detail = ?3 WHERE p.placeDetailID = ?1 ")
	void EditPlace(int placeDetailID ,String placeDetailName , String placeDetail_Detail);
	
	@Modifying
	@Transactional
	@Query("Delete PlaceDetail p where p.placeDetailID = ?1 ")
	void DeletePlace(int placeDetailID);
	
	@Modifying
	@Transactional
	@Query("UPDATE StatusVersion s SET s.statusVersionName = ?1")
	void UpdateStatusVersion(String statusVersionName);
	
	@Query("from StatusVersion")
	StatusVersion getStatusVersion();
}
