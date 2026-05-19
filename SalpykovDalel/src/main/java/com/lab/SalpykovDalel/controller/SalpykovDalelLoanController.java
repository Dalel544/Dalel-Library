package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelLoanRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelLoanResponse;
import com.lab.SalpykovDalel.service.SalpykovDalelLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class SalpykovDalelLoanController {

    private final SalpykovDalelLoanService loanService;

    @GetMapping
    public ResponseEntity<List<SalpykovDalelLoanResponse>> getAll(
            @RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(loanService.getByUserId(userId));
        }
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalpykovDalelLoanResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalpykovDalelLoanResponse> create(
            @Valid @RequestBody SalpykovDalelLoanRequest request) {
        return new ResponseEntity<>(loanService.createLoan(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<SalpykovDalelLoanResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}