package com.tbond.yumdash.dto.user;

import com.tbond.yumdash.common.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder(toBuilder = true)
public class UserRequestDto {
    @NotBlank(message = "fullName is mandatory")
    String fullName;

    @NotBlank(message = "email is mandatory")
    String email;

    @NotBlank(message = "password is mandatory")
    String password;

    MultipartFile avatar;

    String phone;

    Address address;
}
