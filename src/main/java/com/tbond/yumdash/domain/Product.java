package com.tbond.yumdash.domain;

import com.tbond.yumdash.common.ProductSize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Product {
    String id;
    String title;
    String description;
    String image;
    List<ProductSize> productSizes;
    Double rating;
    Double discount;
    String productSlug;
    Category category;
}
