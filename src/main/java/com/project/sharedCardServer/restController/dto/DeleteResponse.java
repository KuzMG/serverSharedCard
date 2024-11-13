package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.target.Target;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    @JsonProperty("delete_id")
    private UUID deleteId;
    @JsonProperty("group_id")
    private UUID groupId;
    @JsonProperty("user_id")
    private UUID userId;
}
