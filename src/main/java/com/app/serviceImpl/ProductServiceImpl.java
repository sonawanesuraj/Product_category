package com.app.serviceImpl;

import java.util.List;

import com.app.dto.IListProductDto;
import com.app.dto.ProductDto;
import com.app.entities.ProductEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ProductRepository;
import com.app.serviceInterface.ProductInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ProductServiceImpl implements ProductInterface {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductName(productDto.getProductName().trim());
		productEntity.setProductPrice(productDto.getProductPrice());
		this.productRepository.save(productEntity);
		return productDto;
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto productDto) {
		ProductEntity productEntity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("Id Not Found "));
		productEntity.setProductName(productDto.getProductName());
		productEntity.setProductPrice(productDto.getProductPrice());
		productRepository.save(productEntity);

		return productDto;
	}

	@Override
	public void deleteProduct(long id) {
		this.productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rosource not found for this id "));
		this.productRepository.deleteById(id);

	}

	@Override
	public List<IListProductDto> getProductById(Long id) {
		List<IListProductDto> iListProductDto;
		return iListProductDto = this.productRepository.findById(id, IListProductDto.class);
	}

	@Override
	public Page<IListProductDto> getAllProduct(String search, String pageNumber, String pageSize) {

		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListProductDto> iListProductDto;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			iListProductDto = productRepository.findByOrderByIdAsc(paging, IListProductDto.class);

		} else {

			iListProductDto = productRepository.findByProductName(search, paging, IListProductDto.class);

		}

		return iListProductDto;

	}

}
