package com.tbond.yumdash.controller;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.UserRole;
import com.tbond.yumdash.dto.user.UserRequestDto;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.mappers.UserMapper;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<UserResponseDto> createUser(@RequestParam String fullName,
                                                      @RequestParam String password,
                                                      @RequestParam String email,
                                                      @RequestParam(required = false) MultipartFile avatar,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false)Address address) {
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

    @GetMapping("/email{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.toUserResponseDto(userService.getUserByEmail(email)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam String fullName,
                                                      @RequestParam String email,
                                                      @RequestParam(required = false) MultipartFile avatar,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false)Address address,
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

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
