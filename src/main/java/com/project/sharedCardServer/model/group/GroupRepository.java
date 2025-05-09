package com.project.sharedCardServer.model.group;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
    @Query(value = "select g.id from \"group\" as g join  group_persons as gu on gu.id_group=g.id " +
            "join account as u on gu.id_person = u.id where u.email = ?1 and g.name = ''", nativeQuery = true)
    UUID getDefaultGroup(String login);

    @Query(value = "select * from \"group\" as g where g.id in " +
            "(select gu.id_group from group_persons as gu where gu.id_person =?1)", nativeQuery = true)
    List<Group> getAllWithDefaultGroup(UUID personId);

    @Query(value = "select * from \"group\" as g where g.id in " +
            "(select gu.id_group from group_persons as gu where gu.id_person =?1) and g.name != ''", nativeQuery = true)
    List<Group> getAllWithoutDefaultGroup(UUID personId);


//    @Modifying
    @Transactional
    @Query(value = "UPDATE \"group\" SET pic = ?2 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Group updatePic(UUID groupId,String pic);

//    @Modifying
    @Transactional
    @Query(value = "update \"group\" set name = ?2 where id = ?1  RETURNING *", nativeQuery = true)
    Group updateName(UUID groupId,String name);
//    @Modifying
    @Transactional
    @Query(value = "update \"group\" set name = ?2, pic = ?3 where id = ?1  RETURNING *", nativeQuery = true)
    Group update(UUID id, String name, String pic);
}
