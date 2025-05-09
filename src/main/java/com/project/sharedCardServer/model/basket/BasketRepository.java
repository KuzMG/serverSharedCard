package com.project.sharedCardServer.model.basket;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BasketRepository extends CrudRepository<Basket, UUID> {

    @Query(value = "select * from basket where id_purchase = ?1", nativeQuery = true)
    List<Basket> get(UUID purchaseId);

    @Modifying
    @Transactional
    @Query(value = "insert into basket (id,id_purchase,count) " +
            "values (?1,?2,?3)",nativeQuery = true)
    void save(UUID id, UUID purchaseId,Double count);
    @Query(value = "select * from basket where id_purchase in (select id from purchase where  id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1))", nativeQuery = true)
    List<Basket> getAll(UUID personId);

    @Transactional
    @Query(value = "delete from basket where id_purchase = ?1 and (select id_basket from history where id_basket=basket.id) is null RETURNING *", nativeQuery = true)
    List<Basket> deleteByPurchaseId(UUID purchaseId);
}
