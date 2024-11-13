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
    private List<UUID> users = new ArrayList<>();
    private List<UUID> groups = new ArrayList<>();
    private List<Pair<UUID,UUID>> groupUsers = new ArrayList<>();
    private List<UUID> checks = new ArrayList<>();
    private List<UUID> targets = new ArrayList<>();
}
