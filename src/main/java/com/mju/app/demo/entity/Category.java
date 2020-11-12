package com.mju.app.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Category")
public class Category {
	
	@Id
	@Column(name="CategoryID")
	private int CategoryID;
	
	@Column(name="CategoryName",columnDefinition = "TEXT")
	private String CategoryName;

	public int getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [CategoryID=" + CategoryID + ", CategoryName=" + CategoryName + "]";
	}

	

	

}