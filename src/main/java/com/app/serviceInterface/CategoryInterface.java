package com.app.serviceInterface;

import java.util.List;

import com.app.dto.CategoryDto;
import com.app.dto.IListCategoryDto;

import org.springframework.data.domain.Page;

public interface CategoryInterface {

	public CategoryDto addCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(Long id, CategoryDto categoryDto);

	public void deleteCategory(Long id);

	public List<IListCategoryDto> getCategoryById(Long id);

	public Page<IListCategoryDto> getAllCategory(String search, String pageNumber, String pageSize);

}
