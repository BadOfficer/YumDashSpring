package com.tbond.yumdash.service;

import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserCreateDto;
import com.tbond.yumdash.repository.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    User createUser(UserCreateDto userCreateDto);

    User getUserByEmail(String email);

    User getUserById(UUID id);

    String updateUserRole(UUID id, UserRole userRole);

    String deleteUser(UUID id);

    Page<UserEntity> getAllUsers(Integer offset, Integer limit);
}
