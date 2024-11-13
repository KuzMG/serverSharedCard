package com.project.sharedCardServer.webSockets;

import com.google.zxing.WriterException;
import com.project.sharedCardServer.model.check.Check;
import com.project.sharedCardServer.model.check.CheckDao;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group.GroupDao;
import com.project.sharedCardServer.model.group.GroupToken;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import com.project.sharedCardServer.model.group_users.GroupUsersDao;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.target.TargetDao;
import com.project.sharedCardServer.model.user.User;
import com.project.sharedCardServer.model.user.UserDao;
import com.project.sharedCardServer.restController.FileManager;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Component
public class StompService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupUsersDao groupUsersDao;
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private TargetDao targetDao;
    @Autowired
    private FileManager fileManager;

    public AccountResponse getAccountResponse(UUID idUser) {
        return new AccountResponse(userDao.getUsers(idUser),
                groupDao.getAll(idUser, false),
                groupUsersDao.getAll(idUser, false),
                checkDao.getAll(idUser),
                targetDao.getAll(idUser));
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final UUID idUser, final String passwordUser) throws AuthenticationException {
        if (!userDao.checkPassword(idUser, passwordUser)) {
            throw new BadCredentialsException("Bad credentials for user ");
        }
        String user = idUser.toString();
        return new UsernamePasswordAuthenticationToken(user,
                "",
                Collections.singleton((GrantedAuthority) () -> "USER"));

    }

    public List<UUID> getUsersId(UUID groupId) {
        return userDao.getUsersId(groupId);
    }

    public List<User> getUsers(UUID userId) {
        return userDao.getUsers(userId);
    }

    public List<User> getAllUsers(UUID groupId) {
        return userDao.getAllUsers(groupId);
    }

    public void saveCheck(Check check) {
        checkDao.save(check);
    }

    public void saveTarget(Target target) {
        targetDao.save(target);
    }

    public void deleteCheck(UUID checkId) {
        checkDao.delete(checkId);
    }

    public void deleteTarget(UUID targetId) {
        targetDao.delete(targetId);
    }

    public Group createGroup(String name, byte[] pic) {
        Group group = groupDao.create(name);
        String uri = FileManager.getDefaulGroupUri(group.getId());
        String path = FileManager.saveGroupPic(uri, pic);
        return groupDao.updatePic(group.getId(), path);
    }

    public GroupUsers createGroupUsers(UUID groupId, UUID userId, Integer status) {
        return groupUsersDao.create(userId, groupId, status);
    }

    public void deleteGroup(UUID groupId) {
        groupDao.delete(groupId);
    }

    public Group updateGroup(UUID id, String name) {
        return groupDao.updateName(id, name);
    }

    public UUID checkToken(String token) {
        return groupDao.checkToken(token);
    }


    public List<Check> getChecks(UUID groupId) {
        return checkDao.get(groupId);
    }

    public List<Target> getTargets(UUID groupId) {
        return targetDao.get(groupId);
    }

    public Group getGroup(UUID groupId) {
        return groupDao.get(groupId);
    }

    public List<GroupUsers> getGroupUsers(UUID groupId) {
        return groupUsersDao.getGroup(groupId);
    }

    public boolean isPasswordValid(UUID userId, String password) {
        return userDao.checkPassword(userId, password);
    }

    public GroupToken getToken(UUID groupId) throws IOException, WriterException {
        return groupDao.getToken(groupId);

    }

    public boolean isUserInGroup(UUID userId, UUID groupId) {
        GroupUsers groupUsers = groupUsersDao.get(userId, groupId);
        return groupUsers != null;
    }

    public boolean deleteUser(UUID userDelId, UUID groupId, UUID userId) {
        GroupUsers groupUsers = groupUsersDao.get(userId, groupId);
        if (groupUsers.getStatus() == GroupUsers.CREATOR ||
                groupUsers.getStatus() == GroupUsers.ADMIN || userDelId.equals(userId)) {
            groupUsersDao.delete(userDelId, groupId);
            return true;
        } else {
            return false;
        }
    }

    public GroupUsers setUserAdmin(UUID userAdminId, UUID groupId, UUID userId, Integer status) {
        GroupUsers groupUsers = groupUsersDao.get(userId, groupId);
        if (groupUsers.getStatus() == GroupUsers.CREATOR) {
            return groupUsersDao.setStatus(userAdminId, groupId, status);
        } else {
            return null;
        }
    }

    public Check getCheck(UUID id) {
        return checkDao.getById(id);
    }

    public Target getTarget(UUID id) {
        return targetDao.getById(id);
    }

    public User updateUser(User user) {
        return userDao.serUser(user);
    }

    public User updateUserPic(UUID userId, String pic) {
        return userDao.updatePic(userId, pic);
    }

    public Group updateGroupPic(UUID idGroup, String pic) {
        return groupDao.updatePic(idGroup, pic);
    }

    public int getStatusOfUser(UUID userId, UUID groupId) {
        return groupUsersDao.getStatus(userId, groupId);
    }

    public User getUser(UUID userId) {
        return userDao.getUser(userId);
    }
}
