package com.tbond.yumdash.controller;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.dto.user.UserUpdateDto;
import com.tbond.yumdash.service.UserProfileService;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserResponseDto> getProfile(Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(userMapper.toUserResponseDto(userProfileService.getUserProfile(userId)));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false) MultipartFile avatar,
                                             @RequestParam(required = false) String phone,
                                             @ModelAttribute Address address,
                                             Authentication authentication) {
        UUID userId = getUserId(authentication);

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .phone(phone)
                .avatar(avatar)
                .build();

        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, userUpdateDto));
    }

    private UUID getUserId(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        return UUID.fromString(user.getId());
    }
}
