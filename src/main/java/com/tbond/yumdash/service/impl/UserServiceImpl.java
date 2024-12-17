package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserRequestDto;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.mappers.UserMapper;
import com.tbond.yumdash.utils.FileUploadUtils;
import jakarta.persistence.PersistenceException;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserServiceImpl implements UserService {
    private static final Double INITIAL_TOTAL_CART_PRICE = 0.0;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileUploadUtils fileUploadUtils;

    @Override
    @Transactional
    public User createUser(UserRequestDto userRequestDto) {
        try {
            String avatarPath = fileUploadUtils.saveImage(userRequestDto.getAvatar());

            CartEntity initialCart = CartEntity.builder()
                    .totalPrice(INITIAL_TOTAL_CART_PRICE)
                    .items(new ArrayList<>())
                    .build();

            UserEntity newUser = UserEntity.builder()
                    .fullName(userRequestDto.getFullName())
                    .email(userRequestDto.getEmail())
                    .address(userRequestDto.getAddress())
                    .phone(userRequestDto.getPhone())
                    .role(UserRole.CUSTOMER)
                    .password(userRequestDto.getPassword())
                    .avatar(avatarPath)
                    .reference(UUID.randomUUID())
                    .cart(initialCart)
                    .build();

            UserEntity savedUser = userRepository.save(newUser);

            return userMapper.toUser(savedUser);
        } catch (Exception e) {
            throw  new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userMapper.toUser(userRepository.findByEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userMapper.toUser(userRepository.findByNaturalId(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public User updateUser(UUID id, UserRequestDto userRequestDto, UserRole userRole) {
        UserEntity user = userRepository.findByNaturalId(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        try {
            String avatarPath = fileUploadUtils.saveImage(userRequestDto.getAvatar());

            user.setAvatar(Optional.ofNullable(avatarPath).orElse(user.getAvatar()));
            user.setFullName(userRequestDto.getFullName());
            user.setEmail(userRequestDto.getEmail());
            user.setAddress(userRequestDto.getAddress());
            user.setPhone(userRequestDto.getPhone());
            user.setRole(Optional.ofNullable(userRole).orElse(user.getRole()));

            return userMapper.toUser(userRepository.save(user));
        } catch (Exception e) {
            throw  new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        getUserById(id);

        try {
            userRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw  new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getAllUsers(Integer offset, Integer limit) {
        return userRepository.findAll(PageRequest.of(offset, limit));
    }
}
