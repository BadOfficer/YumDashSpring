package com.tbond.yumdash.controller;

import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.user.UserCreateDto;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.createUser(userCreateDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserById(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserByEmail(email)));
    }

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<String> updateUserRole(@PathVariable UUID userId,
                                                 @RequestParam UserRole userRole) {
        return ResponseEntity.ok(userService.updateUserRole(userId, userRole));
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponseDto<UserResponseDto>> getAllUsers(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                                             @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Page<UserEntity> users = userService.getAllUsers(offset, limit);

        return ResponseEntity.ok(PaginatedResponseDto.<UserResponseDto>builder()
                .data(userMapper.toUserResponseDtoList(userMapper.toUserList(users.getContent())))
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements()).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
