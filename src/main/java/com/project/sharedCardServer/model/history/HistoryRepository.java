package com.project.sharedCardServer.model.history;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends CrudRepository<History, UUID> {
//    @Query(value = "select * from history where id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1)", nativeQuery = true)
//    List<History> getAll(UUID personId);
//
//    @Query(value = "select * from history where id_group = ?1", nativeQuery = true)
//    List<History> get(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "insert into history (id_basket,id_currency,id_shop,id_person,price,purchase_date) " +
            "values (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void save(UUID basketId,Integer currencyId, Integer shopId, UUID personId, Double price,  Date purchaseDate);


    @Query(value = "select * from history where id_basket in ( select id from basket where id_purchase in (select id from purchase where  id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1)))", nativeQuery = true)
    List<History> getAll(UUID personId);
}
