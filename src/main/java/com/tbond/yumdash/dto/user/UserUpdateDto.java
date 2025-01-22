package com.tbond.yumdash.dto.user;

import com.tbond.yumdash.common.Address;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder(toBuilder = true)
public class UserUpdateDto {
    String firstName;

    String lastName;

    MultipartFile avatar;

    String phone;

    Address address;
}
