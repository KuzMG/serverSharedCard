package com.project.sharedCardServer.model.person;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonDao {
    @Autowired
    private PersonAccountRepository personAccountRepository;
    @Autowired
    private PersonRepository personRepository;

    public Boolean authentication(String login, String password) {
        return checkPassword(login, password) && isVerified(login);
    }

    public Boolean checkPassword(String login, String password) {
        PersonAccount user = personAccountRepository.authentiction(login, password);
        return user != null;
    }

    public Boolean checkPassword(UUID id, String password) {
        PersonAccount account = personAccountRepository.authentiction(id, password);
        return account != null;
    }

    public UUID createPerson(String email, String password, String name, Date birthday, Boolean gender) {
        Person person = new Person(name, gender, birthday);
        UUID id = personRepository.save(person).getId();
        personAccountRepository.save(id, email, password);
        return id;
    }

    public boolean isVerified(String login) {
        Date date = personAccountRepository.getCreationDate(login);
        return date != null;
    }

    public boolean isExist(String login) {
        return getPersonAccount(login) != null;
    }

    public PersonAccount getPersonAccount(String login) {
        return personAccountRepository.getPersonAccount(login);
    }


    public void saveCode(String login, String code, Date now, int i) {
        personAccountRepository.saveCode(login, code, now, i);
    }

    public boolean checkCode(String login, String code) {
        String rightCode = personAccountRepository.getCode(login);
        Date dateCode = personAccountRepository.getDateCode(login);
        Date dateNow = new Date();
        long dateDifference = dateNow.getTime() - dateCode.getTime();
        if (rightCode == null) {
            return false;
        }
        if (dateDifference > 5 * 60 * 1000) {
            return false;
        }
        return rightCode.equals(code);
    }

    public void verification(String login) {
        personAccountRepository.verification(login);
    }

    @Transactional
    public List<Person> getPersons(UUID idUser) {
        Set<Person> persons = personRepository.getPersons(idUser);
        Set<Person> personsPurchase= personRepository.getPersonsPurchase(idUser);
//        Set<Person> usersTarget = personRepository.getUsersCheck(idUser);
        persons.addAll(personsPurchase);
//        people.addAll(usersTarget);
        return persons.stream().toList();
    }

    public List<UUID> getPersonsId(UUID groupId) {
        return personRepository.getPersonsId(groupId);
    }

    @Transactional
    public List<Person> getAllPersons(UUID groupId) {
        Set<Person> persons = personRepository.getPersonsInGroup(groupId);
        Set<Person> personsPurchase = personRepository.getPersonsPurchase(groupId);
//        Set<Person> usersTarget = personRepository.getPersonsPurchase(groupId);
        persons.addAll(personsPurchase);
//        persons.addAll(usersTarget);
        return persons.stream().toList();
    }


    public Person updatePic(UUID id, String pic) {
        Person person = personRepository.findById(id).get();
        person.setPic(pic);
        return personRepository.save(person);
    }

    public Person setPerson(Person person) {
        return personRepository.save(person);
    }

    public Person getPerson(UUID personId) {
        return personRepository.findById(personId).get();
    }
}

