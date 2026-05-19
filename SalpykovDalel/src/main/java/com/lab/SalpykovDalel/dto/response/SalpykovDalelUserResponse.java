package com.lab.SalpykovDalel.dto.response;

import com.lab.SalpykovDalel.entity.enums.SalpykovDalelRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SalpykovDalelUserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;
    private SalpykovDalelRole role;
    private LocalDateTime createdAt;
}