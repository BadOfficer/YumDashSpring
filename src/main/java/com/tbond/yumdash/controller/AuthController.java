package com.tbond.yumdash.controller;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.auth.AuthRequestDto;
import com.tbond.yumdash.dto.auth.AuthResponseDto;
import com.tbond.yumdash.dto.user.UserCreateDto;
import com.tbond.yumdash.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String register(@RequestBody UserCreateDto dto, HttpServletRequest request) {
        User user = authService.register(dto, request);

        return "Success! Check " + user.getEmail() + " to complete your registration!";
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> validateEmail(@RequestParam String token) {
        String verificationMessage = authService.verifyRegistration(token);
        return ResponseEntity.ok(verificationMessage);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
