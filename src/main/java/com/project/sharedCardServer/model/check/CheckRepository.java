package com.project.sharedCardServer.model.check;

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
public interface CheckRepository extends CrudRepository<Check, UUID> {
    @Query(value = "select * from check_ where id_group in (select gu.id_group from group_users as gu where gu.id_user = ?1)", nativeQuery = true)
    List<Check> getAll(UUID idUser);

    @Query(value = "select * from check_ where id_group = ?1", nativeQuery = true)
    List<Check> get(UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "insert into check_  (id,id_product,id_shop,id_currency,id_metric,id_group,id_creator,id_buyer,count,price,description,date_first,date_last,status) " +
            "values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14)",nativeQuery = true)
    void save(UUID id, Integer idProduct,Integer idShop,Integer idCurrency, Integer idMetric, UUID idGroup, UUID idCreator, UUID idBuyer, Integer count,Integer price, String description, Date dateFirst,Date dateLast,Boolean status);

}
