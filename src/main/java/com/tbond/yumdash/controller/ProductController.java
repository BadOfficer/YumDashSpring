package com.tbond.yumdash.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbond.yumdash.common.ProductSize;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.exception.FieldsAndReason;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.mappers.ProductMapper;
import com.tbond.yumdash.utils.ImagesUtils;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.tbond.yumdash.utils.PaymentDetailsUtils.getValidationErrors;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestParam(name = "title") String title,
                                                            @RequestParam(name = "description") String description,
                                                            @RequestParam(name = "image") MultipartFile image,
                                                            @RequestParam(name = "sizes") String sizes,
                                                            @RequestParam(name = "categoryId") Long categoryId,
                                                            @RequestParam(name = "discount") Double discount) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title(title)
                .description(description)
                .sizes(sizes)
                .categoryId(categoryId)
                .image(image)
                .discount(discount).build();

//        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
//            List<FieldsAndReason> fieldsAndReasons = validatorFactory.getValidator().validate(productRequestDto).stream()
//                    .map(violation -> {
//                        return FieldsAndReason.builder()
//                                .field(violation.getPropertyPath().toString())
//                                .reason(violation.getMessage())
//                                .build();
//                    })
//                    .toList();
////            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(fieldsAndReasons));
//            throw new MethodArgumentNotValidException()
//        }

//        Set<ConstraintViolation<ProductRequestDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(productRequestDto);
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
    public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getAllProductsByTitle(@PathVariable String title,
                                                                                          @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                                          @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<ProductEntity> products = productService.getProductsByTitle(title, limit, offset);

        return ResponseEntity.ok(PaginatedResponseDto.<ProductResponseDto>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(products.getContent())))
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages()).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestParam(name = "title") String title,
                                                            @RequestParam(name = "description") String description,
                                                            @RequestParam(name = "image", required = false) MultipartFile image,
                                                            @RequestParam(name = "sizes") String sizes,
                                                            @RequestParam(name = "categoryId") Long categoryId,
                                                            @RequestParam(name = "discount") Double discount) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title(title)
                .description(description)
                .sizes(sizes)
                .categoryId(categoryId)
                .image(image)
                .discount(discount).build();

        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.updateProduct(id, productRequestDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
