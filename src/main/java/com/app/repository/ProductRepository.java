package com.app.repository;

import java.util.List;

import com.app.dto.IListProductDto;
import com.app.entities.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	ProductEntity findByProductNameContainingIgnoreCase(String productName);

	Page<IListProductDto> findByOrderByIdAsc(Pageable paging, Class<IListProductDto> class1);

	Page<IListProductDto> findByProductName(String search, Pageable paging, Class<IListProductDto> class1);

	List<IListProductDto> findById(Long id, Class<IListProductDto> class1);

}
