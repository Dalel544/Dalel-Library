package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelBookRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelBookResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class SalpykovDalelBookController {

    private final SalpykovDalelBookService bookService;

    // ===== MAIN ENDPOINT: pagination + sorting + search + filtering =====
    // Example: GET /api/books?page=0&size=10&sort=title,asc&search=harry&categoryId=1
    @GetMapping
    public ResponseEntity<Page<SalpykovDalelBookResponse>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(bookService.getAll(search, categoryId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalpykovDalelBookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalpykovDalelBookResponse> create(
            @Valid @RequestBody SalpykovDalelBookRequest request) {
        return new ResponseEntity<>(bookService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalpykovDalelBookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SalpykovDalelBookRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}