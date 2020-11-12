package com.mju.app.demo.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.mju.app.demo.entity.StatusVersion;

public interface StatusVersionRepository extends CrudRepository<StatusVersion,Long> {
	@Query("from StatusVersion")
	StatusVersion getStatusServer();
}
