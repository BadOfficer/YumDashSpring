package com.tbond.yumdash.service;

import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserRequestDto;

import java.util.UUID;

public interface UserService {
    User createUser(UserRequestDto userRequestDto);
    User getUserByEmail(String email);
    User getUserById(UUID id);
    User updateUser(UUID id, UserRequestDto userRequestDto, UserRole userRole);
    void deleteUser(UUID id);
}
