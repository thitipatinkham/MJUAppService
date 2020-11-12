package com.mju.app.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mju.app.demo.entity.Category;



public interface CategoryRepository extends CrudRepository<Category, Long>{
	@Query("Select c from Category c where c.CategoryID = ?1")
	Category checkDup(int categoryID);
	
	@Query("from Category")
	List<Category> getCategory();
}
