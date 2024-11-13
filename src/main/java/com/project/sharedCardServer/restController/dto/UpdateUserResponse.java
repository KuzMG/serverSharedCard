package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private User user;
}
