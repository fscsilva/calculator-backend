package com.silvaindustries.calculatorbackend.service.v1;

import com.silvaindustries.calculatorbackend.persistence.repository.UserRepository;
import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUsersByUsername(username)
            .map(user ->  UserDTO.builder()
                .statusActive(user.getStatus())
                .userId(user.getUserId())
                .balance(user.getBalance())
                .password(user.getPassword())
                .username(user.getUsername())
                .authorities(Arrays.stream(user.getRoles().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()))
                .build())
            .orElseThrow(() -> new UserNotFoundException("User not found " + username));
    }

    public static String getPrincipal() {
        return String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}