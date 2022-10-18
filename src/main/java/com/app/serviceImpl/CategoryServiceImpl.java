package com.app.serviceImpl;

import java.util.List;

import com.app.dto.CategoryDto;
import com.app.dto.IListCategoryDto;
import com.app.entities.CategoryEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.CategoryRepository;
import com.app.serviceInterface.CategoryInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class CategoryServiceImpl implements CategoryInterface {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryName(categoryDto.getCategoryName().trim());
		categoryRepository.save(categoryEntity);
		return categoryDto;
	}

	@Override
	public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
		CategoryEntity categoryEntity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("Id Not Found "));
		categoryEntity.setCategoryName(categoryDto.getCategoryName());
		categoryRepository.save(categoryEntity);
		return categoryDto;
	}

	@Override
	public void deleteCategory(Long id) {
		this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rosource not found for this Id "));
		this.categoryRepository.deleteById(id);

	}

	@Override
	public List<IListCategoryDto> getCategoryById(Long id) {
		List<IListCategoryDto> iListCategoryDto;
		return iListCategoryDto = this.categoryRepository.findById(id, IListCategoryDto.class);
	}

	@Override
	public Page<IListCategoryDto> getAllCategory(String search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListCategoryDto> iListCategoryDto;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			iListCategoryDto = categoryRepository.findByOrderByIdAsc(paging, IListCategoryDto.class);

		} else {

			iListCategoryDto = categoryRepository.findByCategoryName(search, paging, IListCategoryDto.class);

		}

		return iListCategoryDto;
	}

}
