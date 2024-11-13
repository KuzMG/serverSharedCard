package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupResponse {
    private String name;
    @JsonProperty("user_id")
    private UUID userId;
    private byte[] pic;
}
