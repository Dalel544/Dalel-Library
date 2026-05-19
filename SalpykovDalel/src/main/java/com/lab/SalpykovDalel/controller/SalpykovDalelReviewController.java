package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelReviewRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelReviewResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class SalpykovDalelReviewController {

    private final SalpykovDalelReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<SalpykovDalelReviewResponse>> getAll(
            @RequestParam(required = false) Long bookId) {
        if (bookId != null) {
            return ResponseEntity.ok(reviewService.getByBookId(bookId));
        }
        return ResponseEntity.ok(reviewService.getAll());
    }

    @PostMapping
    public ResponseEntity<SalpykovDalelReviewResponse> create(
            @RequestParam Long userId,
            @Valid @RequestBody SalpykovDalelReviewRequest request) {
        return new ResponseEntity<>(reviewService.create(userId, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}