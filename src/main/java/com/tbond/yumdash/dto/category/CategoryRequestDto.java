package com.tbond.yumdash.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CategoryRequestDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 100 characters")
    String description;
}
