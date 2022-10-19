package com.app.dto;

public interface IListProductDto {
	public Long getId();

	public IListCategoryDto getCategory();

	public String getProductName();

	public double getProductPrice();

}
