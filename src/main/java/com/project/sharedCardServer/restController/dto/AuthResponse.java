package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("person_id")
    private UUID personId;
    @JsonProperty("group_id")
    private UUID groupId;
}
