package com.app.controller;

import java.util.List;

import com.app.dto.CategoryDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IListCategoryDto;
import com.app.dto.SuccessResponseDto;
import com.app.entities.CategoryEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.CategoryRepository;
import com.app.serviceInterface.CategoryInterface;

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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryInterface categoryInterface;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping()
	public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
		try {
			CategoryEntity categoryEntity = categoryRepository
					.findByCategoryNameContainingIgnoreCase(categoryDto.getCategoryName().trim());
			if (categoryEntity == null) {
				this.categoryInterface.addCategory(categoryDto);
				return new ResponseEntity<>(
						new SuccessResponseDto("Category Added SuccessFully", "CategoryAdded", "Data Added"),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Category already exist", "Please add new Category"),
						HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable long id) {
		try {
			this.categoryInterface.updateCategory(id, categoryDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(" Category updated sucessfully", "Category updated !!", "updated"),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("Category Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			categoryInterface.deleteCategory(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping()
	public ResponseEntity<?> getAllCategory(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "7") String PageSize) {
		Page<IListCategoryDto> category = categoryInterface.getAllCategory(search, PageNo, PageSize);

		if (category.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Categories", "Success", category.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
		try {
			List<IListCategoryDto> iListCategoryDto = this.categoryInterface.getCategoryById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Category:", "Success", iListCategoryDto),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Category not found "),
					HttpStatus.BAD_REQUEST);

		}
	}
}
