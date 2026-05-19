package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelLoginRequest;
import com.lab.SalpykovDalel.dto.request.SalpykovDalelUserRegisterRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelAuthResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import com.lab.SalpykovDalel.entity.enums.SalpykovDalelRole;
import com.lab.SalpykovDalel.exception.SalpykovDalelBadRequestException;
import com.lab.SalpykovDalel.repository.SalpykovDalelUserRepository;
import com.lab.SalpykovDalel.security.SalpykovDalelJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalpykovDalelAuthService {

    private final SalpykovDalelUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SalpykovDalelJwtUtil jwtUtil;
    private final SalpykovDalelNotificationService notificationService;

    public SalpykovDalelAuthResponse register(SalpykovDalelUserRegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());

        // check for duplicates
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new SalpykovDalelBadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new SalpykovDalelBadRequestException("Email already exists");
        }

        // create user
        SalpykovDalelUser user = SalpykovDalelUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .role(SalpykovDalelRole.ROLE_USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        log.info("User registered successfully: {}", user.getUsername());
        notificationService.sendWelcomeEmail(user.getEmail(), user.getUsername());

        // generate JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);

        return new SalpykovDalelAuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public SalpykovDalelAuthResponse login(SalpykovDalelLoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        // authenticate (throws BadCredentialsException if wrong)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SalpykovDalelUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new SalpykovDalelBadRequestException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);
        log.info("User logged in successfully: {}", user.getUsername());

        return new SalpykovDalelAuthResponse(token, user.getUsername(), user.getRole().name());
    }
}