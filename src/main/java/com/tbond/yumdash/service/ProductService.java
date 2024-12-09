package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductRequestDto productDto);
    Product getProductById(UUID productId);
    Product getProductBySlug(String slug);
    Page<ProductEntity> getProductsByCategory(Long categoryId, Integer offset, Integer limit);
    Page<ProductEntity> getProducts(Integer offset, Integer limit);
    Page<ProductEntity> getProductsByTitle(String title, Integer offset, Integer limit);
    Product updateProduct(UUID productId, ProductRequestDto productDto);
    void deleteProduct(UUID productId);
}
