package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.response.SalpykovDalelUserResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import org.springframework.stereotype.Component;

@Component
public class SalpykovDalelUserMapper {

    public SalpykovDalelUserResponse toResponse(SalpykovDalelUser entity) {
        SalpykovDalelUserResponse response = new SalpykovDalelUserResponse();
        response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setAvatarUrl(entity.getAvatarUrl());
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}