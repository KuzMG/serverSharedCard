package com.project.sharedCardServer.model.group;

import com.google.zxing.WriterException;
import com.project.sharedCardServer.restController.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class GroupDao {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    GroupTokenRepository groupTokenRepository;

    public UUID createDefaultGroup() {
        Group group = new Group();
        UUID id = groupRepository.save(group).getId();
        GroupToken groupToken = new GroupToken(id);
        groupTokenRepository.save(groupToken);
        return id;
    }


    public UUID getDefaultGroup(String login) {
        return groupRepository.getDefaultGroup(login);
    }

    public List<Group> getAll(UUID idUser, Boolean enableDefaultGroup) {
        if(enableDefaultGroup){
            return groupRepository.getAllWithDefaultGroup(idUser);
        } else {
            return groupRepository.getAllWithoutDefaultGroup(idUser);
        }
    }

    public Group create(String name) {
        Group group = new Group(name);
        return groupRepository.save(group);
    }

    public void delete(UUID groupId) {
        groupRepository.deleteById(groupId);
    }

    public Group updateName(UUID id, String name) {
        Group group = groupRepository.findById(id).get();
        group.setName(name);
        return groupRepository.save(group);
    }
    public Group updatePic(UUID id, String pic) {
        Group group = groupRepository.findById(id).get();
        group.setPic(pic);
        return groupRepository.save(group);
    }

    public Group update(UUID id, String name, String pic) {
        Group group = groupRepository.findById(id).get();
        group.setName(name);
        group.setPic(pic);
        return groupRepository.save(group);
    }

    public UUID checkToken(String token) {
        GroupToken groupToken = groupTokenRepository.get(token);
        if (groupToken != null) {
            groupToken.setCount(groupToken.getCount() + 1);
            groupTokenRepository.save(groupToken);
            Long tokenTime = groupToken.getDate().getTime();
            Long nowTime = new Date().getTime();
            if (nowTime - tokenTime < 5 * 60 * 1000 &
                    groupToken.getCount() < 25) {
                return groupToken.getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Group get(UUID groupId) {
        return groupRepository.findById(groupId).get();
    }

    public GroupToken getToken(UUID groupId) throws IOException, WriterException {
        GroupToken groupToken = groupTokenRepository.get(groupId);
        if(groupToken == null){
            String token =generateToken();
            String pic = FileManager.createQRImage(groupId, token);
            groupTokenRepository.save(groupId, token,pic);
            return groupTokenRepository.get(groupId);
        }

        Long now = new Date().getTime();
        Long tokenTime = groupToken.getDate().getTime();
        if(now-tokenTime >= 5 * 60 * 1000){
            String token =generateToken();
            String pic = FileManager.createQRImage(groupId, token);
            groupTokenRepository.update(groupId, token,pic);
            return groupTokenRepository.get(groupId);
        } else {
            return groupToken;
        }
    }
    private String generateToken(){
        return UUID.randomUUID().toString();
   }
}
