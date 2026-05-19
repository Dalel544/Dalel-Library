package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelCategoryRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelCategoryResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelCategory;
import org.springframework.stereotype.Component;

@Component
public class SalpykovDalelCategoryMapper {

    public SalpykovDalelCategoryResponse toResponse(SalpykovDalelCategory entity) {
        SalpykovDalelCategoryResponse response = new SalpykovDalelCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public SalpykovDalelCategory toEntity(SalpykovDalelCategoryRequest request) {
        SalpykovDalelCategory entity = new SalpykovDalelCategory();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public void updateEntity(SalpykovDalelCategory entity, SalpykovDalelCategoryRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}