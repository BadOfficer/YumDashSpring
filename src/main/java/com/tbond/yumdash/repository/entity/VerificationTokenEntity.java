package com.tbond.yumdash.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "verification_tokens")
public class VerificationTokenEntity {
    private static final int EXPIRE_TIME = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    Date expiryTime;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "confirmed_at")
    LocalDateTime confirmedAt = null;

    public VerificationTokenEntity(String token, String userId) {
        super();
        this.token = token;
        this.userId = userId;
        this.expiryTime = this.getTokenExpirationTime();
    }

    public VerificationTokenEntity(String token) {
        super();
        this.token = token;
        this.expiryTime = this.getTokenExpirationTime();
    }

    private Date getTokenExpirationTime() {
        return Date.from(LocalDateTime.now().plusMinutes(EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
    }
}
