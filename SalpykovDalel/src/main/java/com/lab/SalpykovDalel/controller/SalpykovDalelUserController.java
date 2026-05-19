package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.response.SalpykovDalelUserResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SalpykovDalelUserController {

    private final SalpykovDalelUserService userService;
    private final com.lab.SalpykovDalel.service.SalpykovDalelNotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<SalpykovDalelUserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalpykovDalelUserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @GetMapping("/{id}/report")
    public CompletableFuture<ResponseEntity<String>> getUserReport(@PathVariable Long id) {
        return notificationService.generateUserActivityReport(id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}