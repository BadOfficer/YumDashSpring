package com.tbond.yumdash.dto.user;

import com.tbond.yumdash.common.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder(toBuilder = true)
public class UserUpdateDto {
    @NotBlank(message = "FirstName is mandatory")
    String firstName;

    @NotBlank(message = "LastName is mandatory")
    String lastName;

    MultipartFile avatar;

    String phone;

    Address address;
}