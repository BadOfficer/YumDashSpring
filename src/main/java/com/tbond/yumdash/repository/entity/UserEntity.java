package com.tbond.yumdash.repository.entity;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String email;

    @Column
    String avatar;

    @Column(unique = true)
    String phone;

    @NaturalId
    @Column(nullable = false, name = "user_reference")
    UUID reference;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    UserRole role;

    Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    CartEntity cart;
}
