package com.tbond.yumdash.security;

import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return UserData.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.getIsEnabled())
                .role(user.getRole())
                .build();
    }
}
