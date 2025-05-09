package com.project.sharedCardServer.restController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeleteResponse {
    private List<UUID> persons = new ArrayList<>();
    private List<UUID> groups = new ArrayList<>();
    private List<Pair<UUID,UUID>> groupPersons = new ArrayList<>();
    private List<UUID> purchases = new ArrayList<>();
    private List<UUID> baskets = new ArrayList<>();
    private List<UUID> targets = new ArrayList<>();
}
