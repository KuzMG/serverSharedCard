package com.project.sharedCardServer.webSockets;

import com.google.zxing.WriterException;
import com.project.sharedCardServer.model.basket.Basket;
import com.project.sharedCardServer.model.basket.BasketDao;
import com.project.sharedCardServer.model.history.History;
import com.project.sharedCardServer.model.history.HistoryDao;
import com.project.sharedCardServer.model.purchase.Purchase;
import com.project.sharedCardServer.model.purchase.PurchaseDao;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group.GroupDao;
import com.project.sharedCardServer.model.group.GroupToken;
import com.project.sharedCardServer.model.group_persons.GroupPersons;
import com.project.sharedCardServer.model.group_persons.GroupPersonsDao;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.target.TargetDao;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.model.person.PersonDao;
import com.project.sharedCardServer.restController.FileManager;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Component
public class StompService {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupPersonsDao groupPersonsDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private TargetDao targetDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private FileManager fileManager;

    public AccountResponse getAccountResponse(UUID personId) {
        return new AccountResponse(personDao.getPersons(personId),
                groupDao.getAll(personId, false),
                groupPersonsDao.getAll(personId, false),
                purchaseDao.getAll(personId),
                basketDao.getAll(personId),
                historyDao.getAll(personId));
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final UUID personId, final String passwordPerson) throws AuthenticationException {
        if (!personDao.checkPassword(personId, passwordPerson)) {
            throw new BadCredentialsException("Bad credentials for person ");
        }
        String person = personId.toString();
        return new UsernamePasswordAuthenticationToken(person,
                "",
                Collections.singleton((GrantedAuthority) () -> "USER"));

    }

    public List<UUID> getPersonsId(UUID groupId) {
        return personDao.getPersonsId(groupId);
    }

    public List<Person> getPersons(UUID userId) {
        return personDao.getPersons(userId);
    }

    public List<Person> getAllPersons(UUID groupId) {
        return personDao.getAllPersons(groupId);
    }

    public void savePurchase(Purchase purchase) {
        purchaseDao.save(purchase);
    }
    public void saveHistory(History history) {
        historyDao.save(history);
    }
    public void saveBasket(Basket basket) {
        basketDao.save(basket);
    }
    public void saveTarget(Target target) {
        targetDao.save(target);
    }

    public void deletePurchase(UUID purchaseId) {
        purchaseDao.delete(purchaseId);
    }
    public void deleteBasket(UUID basketId) {
        basketDao.delete(basketId);
    }

    public void deleteTarget(UUID targetId) {
        targetDao.delete(targetId);
    }

    public Group createGroup(String name, byte[] pic) {
        Group group = groupDao.create(name);
        String uri = FileManager.getDefaulGroupUri(group.getId());
        String path = FileManager.saveGroupPic(uri, pic);
        return groupDao.updatePic(group.getId(), path);
    }

    public GroupPersons createGroupPersons(UUID groupId, UUID personId, Integer status) {
        return groupPersonsDao.create(personId, groupId, status);
    }

    public void deleteGroup(UUID groupId) {
        groupDao.delete(groupId);
    }

    public Group updateGroup(UUID id, String name) {
        return groupDao.updateName(id, name);
    }

    public UUID checkToken(String token) {
        return groupDao.checkToken(token);
    }


    public List<Purchase> getPurchases(UUID groupId) {
        return purchaseDao.get(groupId);
    }

    public List<Target> getTargets(UUID groupId) {
        return targetDao.get(groupId);
    }

    public Group getGroup(UUID groupId) {
        return groupDao.get(groupId);
    }

    public List<GroupPersons> getGroupPersons(UUID groupId) {
        return groupPersonsDao.getGroup(groupId);
    }

    public boolean isPasswordValid(UUID personId, String password) {
        return personDao.checkPassword(personId, password);
    }

    public GroupToken getToken(UUID groupId) throws IOException, WriterException {
        return groupDao.getToken(groupId);

    }

    public boolean isPersonInGroup(UUID personId, UUID groupId) {
        GroupPersons groupPersons = groupPersonsDao.get(personId, groupId);
        return groupPersons != null;
    }

    public boolean deletePerson(UUID personDelId, UUID groupId, UUID personId) {
        GroupPersons groupPersons = groupPersonsDao.get(personId, groupId);
        if (groupPersons.getStatus() == GroupPersons.CREATOR ||
                groupPersons.getStatus() == GroupPersons.ADMIN || personDelId.equals(personId)) {
            groupPersonsDao.delete(personDelId, groupId);
            return true;
        } else {
            return false;
        }
    }

    public GroupPersons setPersonAdmin(UUID personAdminId, UUID groupId, UUID personId, Integer status) {
        GroupPersons groupPersons = groupPersonsDao.get(personId, groupId);
        if (groupPersons.getStatus() == GroupPersons.CREATOR) {
            return groupPersonsDao.setStatus(personAdminId, groupId, status);
        } else {
            return null;
        }
    }

    public Purchase getPurchase(UUID id) {
        return purchaseDao.getById(id);
    }

    public History getHistory(UUID id) {
        return historyDao.getById(id);
    }
    public Basket getBasket(UUID id) {
        return basketDao.getById(id);
    }

    public Target getTarget(UUID id) {
        return targetDao.getById(id);
    }

    public Person updatePerson(Person person) {
        return personDao.setPerson(person);
    }

    public Person updatePersonPic(UUID personId, String pic) {
        return personDao.updatePic(personId, pic);
    }

    public Group updateGroupPic(UUID groupId, String pic) {
        return groupDao.updatePic(groupId, pic);
    }

    public int getStatusOfPerson(UUID personId, UUID groupId) {
        return groupPersonsDao.getStatus(personId, groupId);
    }

    public Person getUser(UUID personId) {
        return personDao.getPerson(personId);
    }

    public List<UUID> deleteAllBasketByPurchase(UUID purchaseId) {
        return basketDao.deleteByPurchaseId(purchaseId);
    }
}
