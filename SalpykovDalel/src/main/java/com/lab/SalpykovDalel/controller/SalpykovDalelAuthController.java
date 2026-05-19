package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelLoginRequest;
import com.lab.SalpykovDalel.dto.request.SalpykovDalelUserRegisterRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelAuthResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SalpykovDalelAuthController {

    private final SalpykovDalelAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SalpykovDalelAuthResponse> register(
            @Valid @RequestBody SalpykovDalelUserRegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<SalpykovDalelAuthResponse> login(
            @Valid @RequestBody SalpykovDalelLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}