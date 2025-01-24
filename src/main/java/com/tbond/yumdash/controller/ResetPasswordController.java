package com.tbond.yumdash.controller;

import com.tbond.yumdash.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reset-password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final ResetPasswordService resetPasswordService;

    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendPasswordResetCode(@PathVariable String email) {
        return ResponseEntity.ok(resetPasswordService.sendResetPasswordCode(email));
    }

    @PostMapping("/confirm/{email}")
    public ResponseEntity<String> confirmResetPassword(@PathVariable String email,
                                                       @RequestParam String code,
                                                       @RequestParam String newPassword) {
        return ResponseEntity.ok(resetPasswordService.resetPassword(email, code, newPassword));
    }
}
