package com.app.dto;

public class ProductDto {
	private Long id;

	private String productName;

	private double productPrice;

	private Long category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public ProductDto(Long id, String productName, double productPrice, Long category) {
		super();
		this.id = id;
		this.productName = productName;
		this.productPrice = productPrice;
		this.category = category;
	}

	public ProductDto() {
		super();
	}

}
