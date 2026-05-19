package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelBookRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelBookResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelAuthor;
import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SalpykovDalelBookMapper {

    public SalpykovDalelBookResponse toResponse(SalpykovDalelBook entity) {
        SalpykovDalelBookResponse response = new SalpykovDalelBookResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setIsbn(entity.getIsbn());
        response.setDescription(entity.getDescription());
        response.setPublicationYear(entity.getPublicationYear());
        response.setPublisher(entity.getPublisher());
        response.setTotalCopies(entity.getTotalCopies());
        response.setAvailableCopies(entity.getAvailableCopies());
        response.setCoverImageUrl(entity.getCoverImageUrl());
        response.setFileUrl(entity.getFileUrl());

        if (entity.getCategory() != null) {
            response.setCategoryName(entity.getCategory().getName());
        }

        if (entity.getAuthors() != null) {
            response.setAuthorNames(
                    entity.getAuthors().stream()
                            .map(a -> a.getFirstName() + " " + a.getLastName())
                            .collect(Collectors.toSet())
            );
        }

        return response;
    }

    public SalpykovDalelBook toEntity(SalpykovDalelBookRequest request) {
        SalpykovDalelBook entity = new SalpykovDalelBook();
        entity.setTitle(request.getTitle());
        entity.setIsbn(request.getIsbn());
        entity.setDescription(request.getDescription());
        entity.setPublicationYear(request.getPublicationYear());
        entity.setPublisher(request.getPublisher());
        entity.setTotalCopies(request.getTotalCopies());
        entity.setAvailableCopies(request.getTotalCopies()); // initially all are available
        return entity;
    }

    public void updateEntity(SalpykovDalelBook entity, SalpykovDalelBookRequest request) {
        entity.setTitle(request.getTitle());
        entity.setIsbn(request.getIsbn());
        entity.setDescription(request.getDescription());
        entity.setPublicationYear(request.getPublicationYear());
        entity.setPublisher(request.getPublisher());
        entity.setTotalCopies(request.getTotalCopies());
    }
}