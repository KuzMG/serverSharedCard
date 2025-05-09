package com.project.sharedCardServer.model.person;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

    @Query(value = "SELECT * FROM person as p " +
            "where p.id in (select group_persons.id_person from group_persons " +
            "where id_group in (select id_group from group_persons where id_person=?1))", nativeQuery = true)
    Set<Person> getPersons(UUID personId);
    @Query(value = "SELECT * FROM person as p " +
            "where p.id in (select purchase.id_person from purchase " +
            "where id_group in (select id_group from group_persons where id_person=?1))", nativeQuery = true)
    Set<Person> getPersonsPurchase(UUID personId);

//    @Query(value = "SELECT * FROM user_ as u " +
//            "where u.id in (select target.id_creator from target " +
//            "where id_group in (select id_group from group_users where id_user=?1)) OR " +
//            "u.id in (select target.id_buyer from target  " +
//            "where id_group in (select id_group from group_users where id_user=?1))", nativeQuery = true)
//    Set<Person> getUsersTarget(UUID idUser);


//    @Query(value = "SELECT * FROM user_ as u " +
//            "where u.id in (select check_.id_creator from check_ " +
//            "where id_group = ?1) OR " +
//            "u.id in (select check_.id_buyer from check_  " +
//            "where id_group = ?1)", nativeQuery = true)
//    Set<Person> getUsersCheckForIdGroup(UUID idGroup);
//
//    @Query(value = "SELECT * FROM user_ as u " +
//            "where u.id in (select target.id_creator from target " +
//            "where id_group = ?1) OR " +
//            "u.id in (select target.id_buyer from target  " +
//            "where id_group = ?1)", nativeQuery = true)
//    Set<Person> getUsersTargetForIdGroup(UUID idGroup);

    @Query(value = "SELECT * FROM person as p " +
            "where p.id in (select group_persons.id_person from group_persons " +
            "where id_group =?1)", nativeQuery = true)
    Set<Person> getPersonsInGroup(UUID idGroup);
    @Query(value = "SELECT id FROM person as p " +
            "where p.id in (select group_persons.id_person from group_persons " +
            "where id_group = ?1)", nativeQuery = true)
    List<UUID> getPersonsId(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "update user_ set pic = ?2 where id = ?1 ", nativeQuery = true)
    void updatePic(UUID id, String pic);

}
