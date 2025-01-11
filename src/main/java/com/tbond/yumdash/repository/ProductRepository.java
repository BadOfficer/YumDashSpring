package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
    ProductEntity findBySlug(String productSlug);

    Page<ProductEntity> findByCategoryId(Long categoryId, Pageable pageable);

    List<ProductEntity> findByCategoryId(Long categoryId);

    List<ProductEntity> findByTitleContainingIgnoreCase(String name);
}
