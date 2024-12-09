package com.tbond.yumdash.dto.product;

import com.tbond.yumdash.common.ProductSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductRequestDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 100 characters")
    String description;

    @NotBlank(message = "Image is mandatory")
    String image;

    List<ProductSize> sizes;

    @NotNull(message = "Category is mandatory")
    Long categoryId;

    @NotNull(message = "Discount is mandatory")
    Double discount;
}