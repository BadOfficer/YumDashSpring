package com.tbond.yumdash.controller;

import com.tbond.yumdash.dto.category.CategoryRequestDto;
import com.tbond.yumdash.dto.category.CategoryResponseDto;
import com.tbond.yumdash.service.CategoryService;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.getCategoryById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDtoList(categoryService.getAllCategories()));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryDto) {
        System.out.println(categoryDto.getTitle());
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.createCategory(categoryDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDto categoryDto) {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.updateCategory(id, categoryDto)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.getCategoryByName(name)));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
