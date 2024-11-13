package com.project.sharedCardServer.model.group;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupTokenRepository extends CrudRepository<GroupToken, UUID> {
    @Query(value = "select * from group_token where token = ?1", nativeQuery = true)
    GroupToken get(String token);
    @Query(value = "select * from group_token where id = ?1", nativeQuery = true)
    GroupToken get(UUID groupId);
    @Modifying
    @Transactional
    @Query(value = "insert into group_token (id,token,date,count,pic) values (?1,?2,now(),0,?3)",nativeQuery = true)
    void save(UUID groupId, String token,String pic);


    @Modifying
    @Transactional
    @Query(value = "update group_token set token=?2,date= now(),count =0,pic =?3 where id=?1",nativeQuery = true)
    void update(UUID groupId, String token,String pic);
}
