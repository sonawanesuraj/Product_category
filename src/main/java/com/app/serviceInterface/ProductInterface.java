package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IListProductDto;
import com.app.dto.ProductDto;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductInterface {

	public ProductDto updateProduct(Long id, ProductDto productDto);

	public void deleteProduct(long id);

	public List<IListProductDto> getProductById(Long id);

	public Page<IListProductDto> getAllProduct(String search, String pageNumber, String pageSize);

	public ProductDto addProduct(ProductDto productDto);

}
