package com.project.sharedCardServer.model.group_persons;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface GroupPersonsRepository extends CrudRepository<GroupPersons, GroupPersonsId> {
    @Modifying
    @Transactional
    @Query(value = "insert into group_persons (id_person,id_group,status,date_invite) values (?1,?2,?3, now())", nativeQuery = true)
    void save(UUID personId, UUID groupId, int status);

    @Query(value = "select * from group_persons where id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1)", nativeQuery = true)
    List<GroupPersons> getAllWithDefaultGroup(UUID personId);

    @Query(value = "select * from group_persons where id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1 and (select name from \"group\" where id = gu.id_group) != '')", nativeQuery = true)
    List<GroupPersons> getAllWithoutDefaultGroup(UUID idUser);
    @Query(value = "select * from group_persons where id_person = ?1 and id_group = ?2", nativeQuery = true)
    GroupPersons get(UUID personId, UUID groupId);
    @Query(value = "select * from group_persons where id_group = ?1", nativeQuery = true)
    List<GroupPersons> getGroup(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "delete from group_persons where id_person = ?1 and id_group = ?2", nativeQuery = true)
    void delete(UUID personId, UUID groupId);
}
