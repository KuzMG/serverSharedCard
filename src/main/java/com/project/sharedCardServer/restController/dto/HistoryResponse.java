package com.project.sharedCardServer.restController.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.basket.Basket;
import com.project.sharedCardServer.model.history.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private History history;
    @JsonProperty("group_id")
    private UUID groupId;
}
