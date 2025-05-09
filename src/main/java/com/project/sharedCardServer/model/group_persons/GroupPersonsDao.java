package com.project.sharedCardServer.model.group_persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GroupPersonsDao {
    @Autowired
    private GroupPersonsRepository groupPersonsRepository;

    public GroupPersons create(UUID personId, UUID groupId, Integer status){
        GroupPersons groupPersons = new GroupPersons(personId,groupId,status, new Date());
        return groupPersonsRepository.save(groupPersons);
    }

    public List<GroupPersons> getAll(UUID personId, Boolean enableDefaultGroup) {
        if(enableDefaultGroup){
            return groupPersonsRepository.getAllWithDefaultGroup(personId);
        } else {
            return groupPersonsRepository.getAllWithoutDefaultGroup(personId);
        }
    }


    public GroupPersons get(UUID personId, UUID groupId) {
        return groupPersonsRepository.get(personId,groupId);
    }

    public List<GroupPersons> getGroup(UUID groupId) {
        return groupPersonsRepository.getGroup(groupId);
    }

    public void delete(UUID personId, UUID groupId) {
        groupPersonsRepository.delete(personId,groupId);
    }

    public GroupPersons setStatus(UUID personAdminId, UUID groupId, int status) {
        GroupPersons groupPersons = groupPersonsRepository.get(personAdminId,groupId);
        groupPersons.setStatus(status);
        return groupPersonsRepository.save(groupPersons);
    }

    public int getStatus(UUID personId, UUID groupId) {
        GroupPersons groupPersons = groupPersonsRepository.get(personId, groupId);
        return groupPersons.getStatus();
    }
}