package com.app.dto;

public class CategoryDto {

	private Long id;

	private String categoryName;

	public CategoryDto(Long id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
