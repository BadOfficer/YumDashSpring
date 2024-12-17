package com.tbond.yumdash.controller;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.user.UserRequestDto;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.mappers.UserMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestParam @NotBlank(message = "Name is mandatory") String fullName,
                                                      @RequestParam @NotNull(message = "Password can't be null") String password,
                                                      @RequestParam @NotBlank(message = "Email is mandatory") String email,
                                                      @RequestParam(required = false) MultipartFile avatar,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false) Address address) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .fullName(fullName)
                .password(password)
                .email(email)
                .address(address)
                .phone(phone)
                .avatar(avatar)
                .build();

        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.createUser(userRequestDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserById(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserByEmail(email)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam @NotBlank(message = "Name is mandatory") String fullName,
                                                      @RequestParam(required = false) @NotBlank(message = "Email is mandatory") String email,
                                                      @RequestParam(required = false) MultipartFile avatar,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false) Address address,
                                                      @RequestParam(required = false) UserRole role,
                                                      @PathVariable UUID id) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .fullName(fullName)
                .email(email)
                .address(address)
                .phone(phone)
                .avatar(avatar)
                .build();

        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.updateUser(id, userRequestDto, role)));
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
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
