package com.project.sharedCardServer.webSockets;

import com.google.zxing.WriterException;
import com.project.sharedCardServer.model.check.Check;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group.GroupToken;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.user.User;
import com.project.sharedCardServer.restController.FileManager;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.CreateGroupResponse;
import com.project.sharedCardServer.restController.dto.JoinInGroupResponse;
import com.project.sharedCardServer.restController.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.project.sharedCardServer.webSockets.StompController.SYNC_PATH_SUBSCRIBE;

@RestController
public class GroupController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StompService stompService;

    @PostMapping("/server/group/join")
    public ResponseEntity<String> joinGroupSync(@RequestBody JoinInGroupResponse response) {
        String token = response.getToken();
        UUID userId = response.getUserId();
        UUID groupId = stompService.checkToken(token);
        if (groupId != null) {
            if (!stompService.isUserInGroup(userId, groupId)) {
                GroupUsers newGroupUsers = stompService.createGroupUsers(groupId, userId, GroupUsers.USER);
                List<Check> checks = stompService.getChecks(groupId);
                List<Target> targets = stompService.getTargets(groupId);
                List<User> users = stompService.getAllUsers(groupId);
                List<GroupUsers> groupUsers = stompService.getGroupUsers(groupId);
                Group group = stompService.getGroup(groupId);
                AccountResponse accountResponseNewUser = new AccountResponse();
                accountResponseNewUser.getChecks().addAll(checks);
                accountResponseNewUser.getTargets().addAll(targets);
                accountResponseNewUser.getUsers().addAll(users);
                accountResponseNewUser.getGroupUsers().addAll(groupUsers);
                accountResponseNewUser.getGroups().add(group);

                AccountResponse accountResponse = new AccountResponse();
                User user = stompService.getUser(userId);
                accountResponse.getUsers().add(user);
                accountResponse.getGroupUsers().add(newGroupUsers);
                for (User u : users) {
                    if (u.getId().equals(userId)) {
                        simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + u.getId(), accountResponseNewUser);
                    } else {
                        simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + u.getId(), accountResponse);
                    }
                }
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный код");
        }
    }

    @GetMapping("/server/group/token")
    public ResponseEntity<TokenResponse> getToken(
            @RequestParam("group-id") UUID groupId,
            @RequestParam("user-id") UUID userId,
            @RequestParam("password") String password
    ) {
        try {
            if (stompService.isPasswordValid(userId, password) &&
                    stompService.isUserInGroup(userId, groupId)) {
                GroupToken token = stompService.getToken(groupId);
                TokenResponse tokenResponse = new TokenResponse(token.getToken(),token.getPic());
                return ResponseEntity.ok(tokenResponse);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/server/group/create")
    public ResponseEntity<String> createGroupSync(@RequestParam("user-id") UUID userCreatorId,
                                                  @RequestParam("password") String password,
                                                  @RequestBody CreateGroupResponse response) {
        if (stompService.isPasswordValid(userCreatorId, password)) {
            UUID userId = response.getUserId();
            String name = response.getName();
            byte[] pic = response.getPic();
            Group group = stompService.createGroup(name, pic);
            GroupUsers groupUsers = stompService.createGroupUsers(group.getId(), userId, GroupUsers.CREATOR);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroups().add(group);
            accountResponse.getGroupUsers().add(groupUsers);
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + userId, accountResponse);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
