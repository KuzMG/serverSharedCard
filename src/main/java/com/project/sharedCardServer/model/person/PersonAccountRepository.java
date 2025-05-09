package com.project.sharedCardServer.model.person;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface PersonAccountRepository extends CrudRepository<PersonAccount, UUID> {
    @Query(value = "SELECT * FROM account where email=?1 and hashed_password = crypt(?2, '$1$0qFFdzRe')", nativeQuery = true)
    PersonAccount authentiction(String login, String password);

    @Query(value = "SELECT * FROM account where id=?1 and hashed_password = crypt(?2, '$1$0qFFdzRe')", nativeQuery = true)
    PersonAccount authentiction(UUID id, String password);
    @Modifying
    @Transactional
    @Query(value = "update account set creation_date=now() where email = ?1", nativeQuery = true)
    void verification(String login);

    @Modifying
    @Transactional
    @Query(value = "insert into account (id,email,hashed_password,count_code) values (?1,?2,crypt(?3, '$1$0qFFdzRe'),0)", nativeQuery = true)
    void save(UUID id, String login, String password);

    @Query(value = "select * from account where email=?1", nativeQuery = true)
    PersonAccount getPersonAccount(String login);

    @Modifying
    @Transactional
    @Query(value = "update account set code=?2, date_code=?3, count_code=?4  where email=?1", nativeQuery = true)
    void saveCode(String login, String code, Date now, int i);

    @Query(value = "select code from account where email=?1", nativeQuery = true)
    String getCode(String login);

    @Query(value = "select date_code from account where email=?1", nativeQuery = true)
    Date getDateCode(String login);
    @Query(value = "select creation_date from account where email=?1", nativeQuery = true)
    Date getCreationDate(String login);
}
