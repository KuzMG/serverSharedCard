package com.project.sharedCardServer.model.purchase;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, UUID> {
    @Query(value = "select * from purchase where id_group in (select gu.id_group from group_persons as gu where gu.id_person = ?1)", nativeQuery = true)
    List<Purchase> getAll(UUID personId);

    @Query(value = "select * from purchase where id_group = ?1", nativeQuery = true)
    List<Purchase> get(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "insert into purchase (id,id_product,id_currency,id_group,id_person,count,price,description,creation_date,is_bought,purchase_date) " +
            "values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)",nativeQuery = true)
    void save(UUID id, Integer productId,Integer currencyId, UUID groupId, UUID personId, Double count,Double price, String description, Date creationDate,Boolean isBought, Date purchaseDate);
    @Modifying
    @Transactional
    @Query(value = "update purchase set is_bought = ?2,purchase_date =?3 where id =?1",nativeQuery = true)
    void update(UUID id,boolean bought, Date purchaseDate);
}
