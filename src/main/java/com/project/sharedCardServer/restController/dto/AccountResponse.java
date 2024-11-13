package com.project.sharedCardServer.restController.dto;

import com.project.sharedCardServer.model.check.Check;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private List<User> users = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<GroupUsers> groupUsers = new ArrayList<>();
    private List<Check> checks = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
}
