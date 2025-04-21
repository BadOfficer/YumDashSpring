package com.tbond.yumdash.dto.user;

import lombok.Data;

@Data
public class CustomerOrderResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String avatar;
}
