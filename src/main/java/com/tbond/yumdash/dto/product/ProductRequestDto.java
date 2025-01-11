package com.tbond.yumdash.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    MultipartFile image;

    @NotBlank(message = "Sizes is mandatory")
    String sizes;

    @NotNull(message = "Category is mandatory")
    Long categoryId;

    @NotNull(message = "Discount is mandatory")
    @DecimalMax(value = "100.0", message = "Discount must be at most 100 percentages")
    Double discount;
}
