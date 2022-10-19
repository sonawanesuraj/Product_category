package com.app.repository;

import java.util.List;

import com.app.dto.IListCategoryDto;
import com.app.entities.CategoryEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	CategoryEntity findByCategoryNameContainingIgnoreCase(String categoryName);

	Page<IListCategoryDto> findByOrderByIdDesc(Pageable page, Class<IListCategoryDto> iListCategoryDto);

	Page<IListCategoryDto> findByCategoryName(String search, Pageable paging, Class<IListCategoryDto> class1);

	List<IListCategoryDto> findById(Long id, Class<IListCategoryDto> class1);

	@Query(value = "SELECT * FROM category c WHERE c.id=:categoryId", nativeQuery = true)
	CategoryEntity findByCategoryId(@Param("categoryId") Long id);

}
