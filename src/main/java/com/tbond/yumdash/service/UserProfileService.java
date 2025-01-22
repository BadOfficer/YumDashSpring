package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserUpdateDto;

import java.util.UUID;

public interface UserProfileService {
    User getUserProfile(UUID userId);
    String updateUserProfile(UUID userId, UserUpdateDto dto);
}
