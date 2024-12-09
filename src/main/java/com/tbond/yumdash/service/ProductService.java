package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductRequestDto productDto);
    Product getProductById(UUID productId);
    Product getProductBySlug(String slug);
    List<Product> getProductsByCategory(Long categoryId);
    PaginatedResponseDto<ProductResponseDto> getProducts(Integer page);
    Product updateProduct(UUID productId, ProductRequestDto productDto);
    void deleteProduct(UUID productId);
}
