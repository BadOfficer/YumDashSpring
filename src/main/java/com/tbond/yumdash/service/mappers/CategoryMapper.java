package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryRequestDto;
import com.tbond.yumdash.dto.category.CategoryResponseDto;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toCategoryList(List<CategoryEntity> categoryEntities);
    CategoryResponseDto toCategoryResponseDto(Category category);
    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categoryList);
}
