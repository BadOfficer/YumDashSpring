package com.tbond.yumdash.domain;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String id;
    String fullName;
    String email;
    String avatar;
    String phone;
    UserRole role;
    Address address;
}
