package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserCreateDto;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.exception.UserExistException;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.mappers.UserMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Double INITIAL_TOTAL_CART_PRICE = 0.0;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        try {
            Optional<UserEntity> existUser = userRepository.findByEmail(userCreateDto.getEmail());

            if (existUser.isPresent()) {
                throw new UserExistException(userCreateDto.getEmail());
            }

            CartEntity initialCart = CartEntity.builder()
                    .totalPrice(INITIAL_TOTAL_CART_PRICE)
                    .items(new ArrayList<>())
                    .build();

            UserEntity newUser = UserEntity.builder()
                    .firstName(userCreateDto.getFirstName())
                    .lastName(userCreateDto.getLastName())
                    .email(userCreateDto.getEmail())
                    .role(UserRole.CUSTOMER)
                    .password(passwordEncoder.encode(userCreateDto.getPassword()))
                    .reference(UUID.randomUUID())
                    .cart(initialCart)
                    .isEnabled(false)
                    .build();

            UserEntity savedUser = userRepository.save(newUser);

            return userMapper.toUser(savedUser);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        UserEntity foundedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return userMapper.toUser(foundedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userMapper.toUser(userRepository.findByNaturalId(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public String updateUserRole(UUID id, UserRole userRole) {
        UserEntity user = userRepository.findByNaturalId(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        user.setRole(userRole);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
        return "User role has been changed";
    }

    @Override
    @Transactional
    public String deleteUser(UUID id) {
        UserEntity user = userRepository.findByNaturalId(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        try {
            userRepository.deleteByNaturalId(id);
            return String.format("User %s deleted successfully", user.getEmail());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getAllUsers(Integer offset, Integer limit) {
        return userRepository.findAll(PageRequest.of(offset, limit));
    }
}
