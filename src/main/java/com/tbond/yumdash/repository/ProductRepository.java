package com.tbond.yumdash.repository;

import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
    ProductEntity findBySlug(String productSlug);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    Page<ProductEntity> findByCategory(Long categoryId, Pageable pageable);

    List<ProductEntity> findByTitleContainingIgnoreCase(String name);
}
