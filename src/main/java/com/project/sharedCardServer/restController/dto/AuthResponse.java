package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("id_user")
    private UUID idUser;
    @JsonProperty("id_group")
    private UUID idGroup;
}
