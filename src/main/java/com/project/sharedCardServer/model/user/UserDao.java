package com.project.sharedCardServer.model.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserDao {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean authentication(String login, String password) {
        return checkPassword(login, password) && isVerified(login);
    }

    public Boolean checkPassword(String login, String password) {
        UserAccount user = userAccountRepository.authentiction(login, password);
        return user != null;
    }

    public Boolean checkPassword(UUID id, String password) {
        UserAccount user = userAccountRepository.authentiction(id, password);
        return user != null;
    }

    public UUID createUser(String email, String password, String name, Date birthday, Boolean gender, Integer height, Double weight) {
        User user = new User(name, gender, weight, height, birthday);
        UUID id = userRepository.save(user).getId();
        userAccountRepository.save(id, email, password);
        return id;
    }

    public boolean isVerified(String login) {
        Date date = userAccountRepository.getRegisterAt(login);
        return date != null;
    }

    public boolean isExist(String login) {
        return getUserAccount(login) != null;
    }

    public UserAccount getUserAccount(String login) {
        return userAccountRepository.getUserAccount(login);
    }


    public void saveCode(String login, String code, Date now, int i) {
        userAccountRepository.saveCode(login, code, now, i);
    }

    public boolean checkCode(String login, String code) {
        String rightCode = userAccountRepository.getCode(login);
        Date dateCode = userAccountRepository.getDateCode(login);
        Date dateNow = new Date();
        long dateDifference = dateNow.getTime() - dateCode.getTime();
        if (rightCode == null) {
            return false;
        }
        if (dateDifference > 5 * 60 * 1000) {
            return false;
        }
        return rightCode.equals(code);
    }

    public void verification(String login) {
        userAccountRepository.verification(login);
    }

    @Transactional
    public List<User> getUsers(UUID idUser) {
        Set<User> users = userRepository.getUsers(idUser);
        Set<User> usersCheck = userRepository.getUsersCheck(idUser);
        Set<User> usersTarget = userRepository.getUsersCheck(idUser);
        users.addAll(usersCheck);
        users.addAll(usersTarget);
        return users.stream().toList();
    }

    public List<UUID> getUsersId(UUID groupId) {
        return userRepository.getUsersId(groupId);
    }

    @Transactional
    public List<User> getAllUsers(UUID groupId) {
        Set<User> users = userRepository.getUsersInGroup(groupId);
        Set<User> usersCheck = userRepository.getUsersCheck(groupId);
        Set<User> usersTarget = userRepository.getUsersCheck(groupId);
        users.addAll(usersCheck);
        users.addAll(usersTarget);
        return users.stream().toList();
    }


    public User updatePic(UUID id, String pic) {
        User user = userRepository.findById(id).get();
        user.setPic(pic);
        return userRepository.save(user);
    }

    public User serUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).get();
    }
}

