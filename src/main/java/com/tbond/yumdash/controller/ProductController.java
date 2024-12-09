package com.tbond.yumdash.controller;

import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.mappers.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.createProduct(productRequestDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.getProductById(id)));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponseDto> getProductBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.getProductBySlug(slug)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PaginatedResponseDto<List<ProductResponseDto>>> getProductsByCategory(@PathVariable Long categoryId,
                                                                                                @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                                @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProductsByCategory(categoryId, limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<List<ProductResponseDto>>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements()).build());
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponseDto<List<ProductResponseDto>>> getAllProducts(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                         @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProducts(limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<List<ProductResponseDto>>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build());
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<PaginatedResponseDto<List<ProductResponseDto>>> getAllProductsByTitle(@PathVariable String title,
                                                                                                @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                                @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProductsByTitle(title, limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<List<ProductResponseDto>>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages()).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.updateProduct(id, productRequestDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
