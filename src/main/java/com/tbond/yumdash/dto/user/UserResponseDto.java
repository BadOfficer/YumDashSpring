package com.tbond.yumdash.dto.user;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserResponseDto {
    String id;
    String firstName;
    String lastName;
    String email;
    String avatar;
    String phone;
    UserRole role;
    Address address;
}
