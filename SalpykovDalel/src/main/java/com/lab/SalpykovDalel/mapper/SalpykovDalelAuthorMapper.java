package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelAuthorRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelAuthorResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelAuthor;
import org.springframework.stereotype.Component;

@Component
public class SalpykovDalelAuthorMapper {

    public SalpykovDalelAuthorResponse toResponse(SalpykovDalelAuthor entity) {
        SalpykovDalelAuthorResponse response = new SalpykovDalelAuthorResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setBirthDate(entity.getBirthDate());
        response.setBiography(entity.getBiography());
        response.setNationality(entity.getNationality());
        return response;
    }

    public SalpykovDalelAuthor toEntity(SalpykovDalelAuthorRequest request) {
        SalpykovDalelAuthor entity = new SalpykovDalelAuthor();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setBiography(request.getBiography());
        entity.setNationality(request.getNationality());
        return entity;
    }

    public void updateEntity(SalpykovDalelAuthor entity, SalpykovDalelAuthorRequest request) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setBiography(request.getBiography());
        entity.setNationality(request.getNationality());
    }
}