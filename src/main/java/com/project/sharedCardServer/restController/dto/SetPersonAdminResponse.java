package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPersonAdminResponse {
    @JsonProperty("user_admin_id")
    private UUID personAdminId;
    @JsonProperty("group_id")
    private UUID groupId;
    @JsonProperty("user_id")
    private UUID perosnId;
    private Integer status;
}
