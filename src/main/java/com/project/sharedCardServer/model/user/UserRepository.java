package com.project.sharedCardServer.model.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select group_users.id_user from group_users " +
            "where id_group in (select id_group from group_users where id_user=?1))", nativeQuery = true)
    Set<User> getUsers(UUID idUser);
    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select check_.id_creator from check_ " +
            "where id_group in (select id_group from group_users where id_user=?1)) OR " +
            "u.id in (select check_.id_buyer from check_  " +
            "where id_group in (select id_group from group_users where id_user=?1))", nativeQuery = true)
    Set<User> getUsersCheck(UUID idUser);

    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select target.id_creator from target " +
            "where id_group in (select id_group from group_users where id_user=?1)) OR " +
            "u.id in (select target.id_buyer from target  " +
            "where id_group in (select id_group from group_users where id_user=?1))", nativeQuery = true)
    Set<User> getUsersTarget(UUID idUser);


    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select check_.id_creator from check_ " +
            "where id_group = ?1) OR " +
            "u.id in (select check_.id_buyer from check_  " +
            "where id_group = ?1)", nativeQuery = true)
    Set<User> getUsersCheckForIdGroup(UUID idGroup);

    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select target.id_creator from target " +
            "where id_group = ?1) OR " +
            "u.id in (select target.id_buyer from target  " +
            "where id_group = ?1)", nativeQuery = true)
    Set<User> getUsersTargetForIdGroup(UUID idGroup);

    @Query(value = "SELECT * FROM user_ as u " +
            "where u.id in (select group_users.id_user from group_users " +
            "where id_group =?1)", nativeQuery = true)
    Set<User> getUsersInGroup(UUID idGroup);
    @Query(value = "SELECT id FROM user_ as u " +
            "where u.id in (select group_users.id_user from group_users " +
            "where id_group = ?1)", nativeQuery = true)
    List<UUID> getUsersId(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "update user_ set pic = ?2 where id = ?1 ", nativeQuery = true)
    void updatePic(UUID id, String pic);

}
