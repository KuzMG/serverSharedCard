package com.project.sharedCardServer.model.group_users;

import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GroupUsersDao {
    @Autowired
    private GroupUsersRepository groupUsersRepository;

    public GroupUsers create(UUID userId, UUID groupId,Integer status){
        GroupUsers groupUsers = new GroupUsers(userId,groupId,status, new Date());
        return groupUsersRepository.save(groupUsers);
    }

    public List<GroupUsers> getAll(UUID idUser, Boolean enableDefaultGroup) {
        if(enableDefaultGroup){
            return groupUsersRepository.getAllWithDefaultGroup(idUser);
        } else {
            return groupUsersRepository.getAllWithoutDefaultGroup(idUser);
        }
    }


    public GroupUsers get(UUID userId, UUID groupId) {
        return groupUsersRepository.get(userId,groupId);
    }

    public List<GroupUsers> getGroup(UUID groupId) {
        return groupUsersRepository.getGroup(groupId);
    }

    public void delete(UUID userId, UUID groupId) {
        groupUsersRepository.delete(userId,groupId);
    }

    public GroupUsers setStatus(UUID userAdminId, UUID groupId, int status) {
        GroupUsers groupUsers = groupUsersRepository.get(userAdminId,groupId);
        groupUsers.setStatus(status);
        return groupUsersRepository.save(groupUsers);
    }

    public int getStatus(UUID userId, UUID groupId) {
        GroupUsers groupUsers = groupUsersRepository.get(userId, groupId);
        return groupUsers.getStatus();
    }
}