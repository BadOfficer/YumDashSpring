package com.tbond.yumdash.dto.product;

import com.tbond.yumdash.common.ProductSize;
import com.tbond.yumdash.dto.category.CategoryResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductResponseDto {
    String id;
    String title;
    String description;
    String image;
    Double rating;
    List<ProductSize> sizes;
    Double discount;
    String productSlug;
    CategoryResponseDto category;
}
