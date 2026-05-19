package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.response.SalpykovDalelReviewResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelReview;
import org.springframework.stereotype.Component;

@Component
public class SalpykovDalelReviewMapper {

    public SalpykovDalelReviewResponse toResponse(SalpykovDalelReview entity) {
        SalpykovDalelReviewResponse response = new SalpykovDalelReviewResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUser().getId());
        response.setUsername(entity.getUser().getUsername());
        response.setBookId(entity.getBook().getId());
        response.setBookTitle(entity.getBook().getTitle());
        response.setRating(entity.getRating());
        response.setComment(entity.getComment());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}