package com.project.sharedCardServer.webSockets;


import com.project.sharedCardServer.model.check.Check;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.user.User;
import com.project.sharedCardServer.restController.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class StompController {
    private static final String SYNC_FULL_PATH_SUBSCRIBE = "/app/synchronization/full/";
    public static final String SYNC_PATH_SUBSCRIBE = "/app/synchronization/";
    private static final String SYNC_DELETE_PATH_SUBSCRIBE = "/app/synchronization/delete/";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StompService stompService;


    @MessageMapping("/user/update")
    public void updateUserSync(UpdateUserResponse response) {
        User user = stompService.updateUser(response.getUser());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getUsers().add(user);
        List<User> users = stompService.getUsers(user.getId());
        for (User u : users) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + u.getId(), accountResponse);
        }
    }

    @MessageMapping("/group/update")
    public void updateGroupSync(UpdateGroupResponse response) {
        String name = response.getName();
        UUID groupId = response.getGroupId();
        Group group = stompService.updateGroup(groupId, name);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getGroups().add(group);
        List<UUID> users = stompService.getUsersId(groupId);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + user, accountResponse);
        }
    }

    @MessageMapping("/group/delete")
    public void deleteGroupSync(DeleteResponse response) {
        UUID groupId = response.getGroupId();
        UUID userId = response.getUserId();
        if (stompService.isUserInGroup(userId, groupId)) {

            int status = stompService.getStatusOfUser(userId, groupId);
            List<UUID> users = stompService.getUsersId(groupId);
            if (status == GroupUsers.CREATOR) {
                stompService.deleteGroup(groupId);
                AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
                accountDeleteResponse.getGroups().add(groupId);
                for (UUID user : users) {
                    simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteResponse);
                }
            } else {
                stompService.deleteUser(userId, groupId, userId);
                AccountDeleteResponse accountDeleteGroupResponse = new AccountDeleteResponse();
                accountDeleteGroupResponse.getGroups().add(groupId);

                AccountDeleteResponse accountDeleteUserResponse = new AccountDeleteResponse();
                accountDeleteUserResponse.getGroupUsers().add(Pair.of(groupId, userId));
                for (UUID user : users) {
                    if (user.equals(userId)) {
                        simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteGroupResponse);
                    } else {
                        simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteUserResponse);
                    }
                }
            }
        }
    }

    @MessageMapping("/group/user/delete")
    public void deleteUserSync(DeleteResponse response) {
        UUID userDelId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        UUID userId = response.getUserId();
        if (stompService.deleteUser(userDelId, groupId, userId)) {
            AccountDeleteResponse responseDeleteUser = new AccountDeleteResponse();
            responseDeleteUser.getGroups().add(groupId);
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + userDelId, responseDeleteUser);

            List<UUID> users = stompService.getUsersId(groupId);
            AccountDeleteResponse responseGroup = new AccountDeleteResponse();
            responseGroup.getGroupUsers().add(Pair.of(groupId, userDelId));
            for (UUID user : users) {
                simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, responseGroup);
            }
        }
    }

    @MessageMapping("/group/user/admin")
    public void setUserAdminSync(SetUserAdminResponse response) {
        UUID userAdminId = response.getUserAdminId();
        UUID groupId = response.getGroupId();
        UUID userId = response.getUserId();
        Integer status = response.getStatus();
        GroupUsers groupUsers = stompService.setUserAdmin(userAdminId, groupId, userId,status);
        if (groupUsers != null) {
            List<UUID> users = stompService.getUsersId(groupId);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroupUsers().add(groupUsers);
            for (UUID user : users) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + user, accountResponse);
            }
        }
    }

    @MessageMapping("/check")
    public void checkSync(CheckResponse response) {
        Check check = response.getCheck();
        UUID groupId = response.getGroupId();
        List<UUID> users = stompService.getUsersId(groupId);
        stompService.saveCheck(check);
        Check newCheck = stompService.getCheck(check.getId());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getChecks().add(newCheck);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + user, accountResponse);
        }

    }

    @MessageMapping("/target")
    public void targetSync(TargetResponse response) {
        Target target = response.getTarget();
        UUID groupId = response.getGroupId();
        List<UUID> users = stompService.getUsersId(groupId);
        stompService.saveTarget(target);
        Target newTarget = stompService.getTarget(target.getId());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getTargets().add(newTarget);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + user, accountResponse);
        }
    }

    @MessageMapping("/check/delete")
    public void checkDeleteSync(DeleteResponse response) {
        UUID checkId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        List<UUID> users = stompService.getUsersId(groupId);
        stompService.deleteCheck(checkId);
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.getChecks().add(checkId);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteResponse);
        }

    }

    @MessageMapping("/target/delete")
    public void targetDeleteSync(DeleteResponse response) {
        UUID targetId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        List<UUID> users = stompService.getUsersId(groupId);
        stompService.deleteTarget(targetId);
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.getTargets().add(targetId);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteResponse);
        }
    }

    public void sync(UUID idUser) {
        AccountResponse accountResponse = stompService.getAccountResponse(idUser);
        simpMessagingTemplate.convertAndSend(SYNC_FULL_PATH_SUBSCRIBE + idUser, accountResponse);
    }
}
