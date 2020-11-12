package com.mju.app.demo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.repo.CategoryRepository;
import com.mju.app.demo.util.ResponseObj;



@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Category")
public class CategoryController {
	@Autowired
	CategoryRepository mCategoryRepository;
	
	@PostMapping(path="add")
	public @ResponseBody ResponseObj addNewCategory(@RequestBody Category category)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		Category c = category;
		
		if(mCategoryRepository.checkDup(c.getCategoryID()) != null) {
			return new ResponseObj(500, "Dupplicated user!!");
		}else {
			mCategoryRepository.save(c);
			return new ResponseObj(200, c);
		}
	}
	
	@PostMapping(path="/getCategory")
	public @ResponseBody ResponseObj getCategory() {
		List<Category> list = mCategoryRepository.getCategory();
		List<Category> listcategory = new ArrayList<>();
		listcategory.add(list.get(0));
		listcategory.add(list.get(1));
		listcategory.add(list.get(4));
		listcategory.add(list.get(5));
		listcategory.add(list.get(7));
		listcategory.add(list.get(9));
		listcategory.add(list.get(2));
		listcategory.add(list.get(10));
		listcategory.add(list.get(8));
		listcategory.add(list.get(6));
		listcategory.add(list.get(3));
		
		return new ResponseObj(200,listcategory);
	}

}
