package com.tbond.yumdash.controller;

import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.mappers.ProductMapper;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ProductResponseDto> createProduct(@RequestParam(name = "title") @NotBlank(message = "Title is mandatory") String title,
                                                            @RequestParam(name = "description") @NotBlank(message = "Description is mandatory") String description,
                                                            @RequestParam(name = "image") MultipartFile image,
                                                            @RequestParam(name = "sizes") @NotNull(message = "Sizes can't be null") String sizes,
                                                            @RequestParam(name = "categoryId") @NotNull(message = "Category can't be null") Long categoryId,
                                                            @RequestParam(name = "discount", required = false)
                                                            @DecimalMax(value = "100.0", message = "Discount must be at most 100") Double discount) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title(title)
                .description(description)
                .sizes(sizes)
                .categoryId(categoryId)
                .image(image)
                .discount(discount)
                .build();

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
    public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getProductsByCategory(@PathVariable Long categoryId,
                                                                                          @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                          @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProductsByCategory(categoryId, limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<ProductResponseDto>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements()).build());
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getAllProducts(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                   @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProducts(limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<ProductResponseDto>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build());
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(productMapper.toProductResponseDtoList(productService.getProductsByTitle(title)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestParam(name = "title", required = false) String title,
                                                            @RequestParam(name = "description", required = false) String description,
                                                            @RequestParam(name = "image", required = false) MultipartFile image,
                                                            @RequestParam(name = "sizes", required = false) String sizes,
                                                            @RequestParam(name = "categoryId", required = false) Long categoryId,
                                                            @RequestParam(name = "discount", required = false) Double discount) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title(title)
                .description(description)
                .sizes(sizes)
                .categoryId(categoryId)
                .image(image)
                .discount(discount)
                .build();

        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.updateProduct(id, productRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
