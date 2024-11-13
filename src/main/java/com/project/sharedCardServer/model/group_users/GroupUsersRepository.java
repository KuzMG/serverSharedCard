package com.project.sharedCardServer.model.group_users;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface GroupUsersRepository extends CrudRepository<GroupUsers, GroupUsersId> {
    @Modifying
    @Transactional
    @Query(value = "insert into group_users (id_user,id_group,status,date_invite) values (?1,?2,?3, now())", nativeQuery = true)
    void save(UUID idUser, UUID idGroup, int status);

    @Query(value = "select * from group_users where id_group in (select gu.id_group from group_users as gu where gu.id_user = ?1)", nativeQuery = true)
    List<GroupUsers> getAllWithDefaultGroup(UUID idUser);

    @Query(value = "select * from group_users where id_group in (select gu.id_group from group_users as gu where gu.id_user = ?1 and (select name from group_ where id = gu.id_group) != '')", nativeQuery = true)
    List<GroupUsers> getAllWithoutDefaultGroup(UUID idUser);
    @Query(value = "select * from group_users where id_user = ?1 and id_group = ?2", nativeQuery = true)
    GroupUsers get(UUID userId, UUID groupId);
    @Query(value = "select * from group_users where id_group = ?1", nativeQuery = true)
    List<GroupUsers> getGroup(UUID groupId);

    @Modifying
    @Transactional

    @Query(value = "delete from group_users where id_user = ?1 and id_group = ?2", nativeQuery = true)
    void delete(UUID userId, UUID groupId);
}
