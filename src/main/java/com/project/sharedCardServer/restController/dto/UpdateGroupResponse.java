package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupResponse {
    private String name;
    @JsonProperty("group_id")
    private UUID groupId;
}
