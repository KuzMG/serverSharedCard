package com.project.sharedCardServer.model.target;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Repository
public interface TargetRepository extends CrudRepository<Target, UUID> {
    @Query(value = "select * from target where id_group in (select gu.id_group from group_users as gu where gu.id_user = ?1)", nativeQuery = true)
    List<Target> getAll(UUID idUser);
    @Query(value = "select * from target where id_group = ?1", nativeQuery = true)
    List<Target> get(UUID groupId);


    @Modifying
    @Transactional
    @Query(value = "insert into target  (id,name,id_group,id_category,id_shop,price_first,price_last,id_currency_first,id_currency_last,id_creator,id_buyer,date_first,date_last,status) " +
            "values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14)",nativeQuery = true)
    void save(UUID id,String name, UUID idGroup, Integer idCategory,Integer idShop,Integer priceFirst,Integer priceLast, Integer idCurrencyFirst, Integer idCurrencyLast, UUID idCreator, UUID idBuyer, Date dateFirst,Date dateLast, Boolean status);

}
