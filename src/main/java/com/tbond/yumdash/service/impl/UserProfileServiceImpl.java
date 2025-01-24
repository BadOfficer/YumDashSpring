package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserUpdateDto;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.FileService;
import com.tbond.yumdash.service.UserProfileService;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.mappers.UserMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Override
    @Transactional(readOnly = true)
    public User getUserProfile(UUID userId) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));

        return userMapper.toUser(user);
    }

    @Override
    @Transactional
    public String updateUserProfile(UUID userId, UserUpdateDto dto) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        String avatarUrl = user.getAvatar();

        if (dto.getAvatar() != null) {
            avatarUrl = fileService.uploadImage(dto.getAvatar());
        }

        user.setFirstName(Optional.ofNullable(dto.getFirstName()).orElse(user.getFirstName()));
        user.setLastName(Optional.ofNullable(dto.getLastName()).orElse(user.getLastName()));
        user.setAddress(Optional.ofNullable(dto.getAddress()).orElse(user.getAddress()));
        user.setPhone(Optional.ofNullable(dto.getPhone()).orElse(user.getPhone()));
        user.setAvatar(avatarUrl);

        try {
            userRepository.save(user);
            return "User  has been updated successfully";
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
