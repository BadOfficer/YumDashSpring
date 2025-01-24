package com.tbond.yumdash.repository.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
@Table(name = "reset_password_codes")
@NoArgsConstructor
public class ResetPasswordCodeEntity {
    private final static int EXPIRE_TIME = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String verificationCode;

    Date expiryTime;

    @Column(name = "user_email", nullable = false)
    String userEmail;

    public ResetPasswordCodeEntity(String verificationCode, String userEmail) {
        super();
        this.verificationCode = verificationCode;
        this.userEmail = userEmail;
        this.expiryTime = this.getCodeExpirationTime();
    }

    private Date getCodeExpirationTime() {
        return Date.from(LocalDateTime.now().plusMinutes(EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
    }
}
