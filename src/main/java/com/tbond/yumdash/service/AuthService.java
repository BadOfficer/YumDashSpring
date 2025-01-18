package com.tbond.yumdash.service;


import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.auth.AuthRequestDto;
import com.tbond.yumdash.dto.auth.AuthResponseDto;
import com.tbond.yumdash.dto.user.UserCreateDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    User register(UserCreateDto dto, HttpServletRequest request);

    String verifyRegistration(String token);

    AuthResponseDto login(AuthRequestDto dto);
}
