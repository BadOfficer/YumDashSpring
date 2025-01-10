package com.tbond.yumdash.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDto {
    @NotBlank(message = "FirstName is mandatory")
    String firstName;

    @NotBlank(message = "LastName is mandatory")
    String lastName;

    @NotBlank(message = "Email is mandatory")
    String email;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    String password;
}
