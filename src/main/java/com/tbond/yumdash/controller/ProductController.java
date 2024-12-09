package com.tbond.yumdash.controller;

import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.mappers.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid final ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.createProduct(productRequestDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable final UUID id) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.getProductById(id)));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponseDto> getProductBySlug(@PathVariable final String slug) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.getProductBySlug(slug)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable final Long categoryId) {
        return ResponseEntity.ok(productMapper.toProductResponseDtoList(productService.getProductsByCategory(categoryId)));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getAllProducts(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(productService.getProducts(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.updateProduct(id, productRequestDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable final UUID id) {
        productService.deleteProduct(id);
    }
}
