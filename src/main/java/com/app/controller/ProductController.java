package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListProductDto;
import com.app.dto.ProductDto;
import com.app.dto.SuccessResponseDto;
import com.app.entities.ProductEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ProductRepository;
import com.app.serviceInterface.ProductInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductInterface productInterface;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping()
	public ResponseEntity<?> addProduct(Long categoryId, @RequestBody ProductDto productDto) {
		try {
			ProductEntity productEntity = productRepository
					.findByProductNameContainingIgnoreCase(productDto.getProductName().trim());
			if (productEntity == null) {
				this.productInterface.addProduct(productDto);
				return new ResponseEntity<>(
						new SuccessResponseDto("Product added successfully", "ProductAdded", "Data added"),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Product already exist", "Please add new Product"),
						HttpStatus.BAD_REQUEST);
			}

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.ok(new ErrorResponseDto("Enter valid Product", "Invalid Product"));
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto, @PathVariable long id) {
		try {
			this.productInterface.updateProduct(id, productDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(" Product updated sucessfully", "product updated !!", "updated"),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("product Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			productInterface.deleteProduct(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping()
	public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "7") String PageSize) {
		Page<IListProductDto> product = productInterface.getAllProduct(search, PageNo, PageSize);

		if (product.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Product", "Success", product.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		try {
			List<IListProductDto> iListProductDto = this.productInterface.getProductById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Products:", "Success", iListProductDto),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Product not found "),
					HttpStatus.BAD_REQUEST);

		}
	}

}
