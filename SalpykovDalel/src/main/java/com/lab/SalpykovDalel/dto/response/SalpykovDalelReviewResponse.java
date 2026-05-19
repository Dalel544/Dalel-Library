package com.lab.SalpykovDalel.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SalpykovDalelReviewResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}