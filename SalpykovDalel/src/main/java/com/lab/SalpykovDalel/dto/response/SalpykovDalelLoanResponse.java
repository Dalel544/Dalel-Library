package com.lab.SalpykovDalel.dto.response;

import com.lab.SalpykovDalel.entity.enums.SalpykovDalelLoanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SalpykovDalelLoanResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private SalpykovDalelLoanStatus status;
    private Double fineAmount;
}