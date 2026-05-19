package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelAuthorRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelAuthorResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class SalpykovDalelAuthorController {

    private final SalpykovDalelAuthorService authorService;

    @GetMapping
    public ResponseEntity<List<SalpykovDalelAuthorResponse>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalpykovDalelAuthorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalpykovDalelAuthorResponse> create(
            @Valid @RequestBody SalpykovDalelAuthorRequest request) {
        return new ResponseEntity<>(authorService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalpykovDalelAuthorResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SalpykovDalelAuthorRequest request) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}