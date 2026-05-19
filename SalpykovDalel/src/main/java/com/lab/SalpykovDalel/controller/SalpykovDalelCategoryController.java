package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelCategoryRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelCategoryResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class SalpykovDalelCategoryController {

    private final SalpykovDalelCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<SalpykovDalelCategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalpykovDalelCategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalpykovDalelCategoryResponse> create(
            @Valid @RequestBody SalpykovDalelCategoryRequest request) {
        return new ResponseEntity<>(categoryService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalpykovDalelCategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SalpykovDalelCategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}