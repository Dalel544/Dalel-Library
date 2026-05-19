package com.lab.SalpykovDalel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalpykovDalelAuthResponse {
    private String token;
    private String username;
    private String role;
}