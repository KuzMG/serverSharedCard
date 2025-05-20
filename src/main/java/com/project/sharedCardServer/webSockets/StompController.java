package com.project.sharedCardServer.webSockets;


import com.project.sharedCardServer.model.basket.Basket;
import com.project.sharedCardServer.model.history.History;
import com.project.sharedCardServer.model.purchase.Purchase;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group_persons.GroupPersons;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.restController.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class StompController {
    private static final String SYNC_FULL_PATH_SUBSCRIBE = "/exchange/amq.topic/synchronization/full/";
    public static final String SYNC_PATH_SUBSCRIBE = "/exchange/amq.topic/synchronization/";
    private static final String SYNC_DELETE_PATH_SUBSCRIBE = "/exchange/amq.topic/synchronization/delete/";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StompService stompService;


    @MessageMapping("/person/update")
    public void updatePersonSync(UpdatePersonResponse response) {
        Person person = stompService.updatePerson(response.getPerson());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getPersons().add(person);
        List<Person> persons = stompService.getPersons(person.getId());
        for (Person p : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + p.getId(), accountResponse);
        }
    }

    @MessageMapping("/group/update")
    public void updateGroupSync(UpdateGroupResponse response) {
        String name = response.getName();
        UUID groupId = response.getGroupId();
        Group group = stompService.updateGroup(groupId, name);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getGroups().add(group);
        List<UUID> persons = stompService.getPersonsId(groupId);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
        }
    }

    @MessageMapping("/group/delete")
    public void deleteGroupSync(DeleteResponse response) {
        UUID groupId = response.getGroupId();
        UUID personId = response.getPersonId();
        if (stompService.isPersonInGroup(personId, groupId)) {

            int status = stompService.getStatusOfPerson(personId, groupId);
            List<UUID> persons = stompService.getPersonsId(groupId);
            if (status == GroupPersons.CREATOR) {
                stompService.deleteGroup(groupId);
                AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
                accountDeleteResponse.getGroups().add(groupId);
                for (UUID person : persons) {
                    simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteResponse);
                }
            } else {
                stompService.deletePerson(personId, groupId, personId);
                AccountDeleteResponse accountDeleteGroupResponse = new AccountDeleteResponse();
                accountDeleteGroupResponse.getGroups().add(groupId);

                AccountDeleteResponse accountDeleteUserResponse = new AccountDeleteResponse();
                accountDeleteUserResponse.getGroupPersons().add(Pair.of(groupId, personId));
                for (UUID person : persons) {
                    if (person.equals(personId)) {
                        simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteGroupResponse);
                    } else {
                        simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteUserResponse);
                    }
                }
            }
        }
    }

    @MessageMapping("/group/person/delete")
    public void deletePersonSync(DeleteResponse response) {
        UUID personDelId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        UUID personId = response.getPersonId();
        if (stompService.deletePerson(personDelId, groupId, personId)) {
            AccountDeleteResponse responseDeletePerson = new AccountDeleteResponse();
            responseDeletePerson.getGroups().add(groupId);
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + personDelId, responseDeletePerson);

            List<UUID> persons = stompService.getPersonsId(groupId);
            AccountDeleteResponse responseGroup = new AccountDeleteResponse();
            responseGroup.getGroupPersons().add(Pair.of(groupId, personDelId));
            for (UUID person : persons) {
                simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, responseGroup);
            }
        }
    }

    @MessageMapping("/group/person/admin")
    public void setPersonAdminSync(SetPersonAdminResponse response) {
        UUID personAdminId = response.getPersonAdminId();
        UUID groupId = response.getGroupId();
        UUID personId = response.getPerosnId();
        Integer status = response.getStatus();
        GroupPersons groupPersons = stompService.setPersonAdmin(personAdminId, groupId, personId, status);
        if (groupPersons != null) {
            List<UUID> persons = stompService.getPersonsId(groupId);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroupPersons().add(groupPersons);
            for (UUID person : persons) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
            }
        }
    }

    @MessageMapping("/purchase")
    public void purchaseSync(PurchaseResponse response) {
        Purchase purchase = response.getPurchase();
        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        stompService.savePurchase(purchase);
        Purchase newPurchase = stompService.getPurchase(purchase.getId());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getPurchases().add(newPurchase);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
        }
    }

    @MessageMapping("/purchase/history")
    public void purchaseToHistorySync(PurchaseResponse response) {
        Purchase purchase = response.getPurchase();

        List<UUID> baskets = stompService.deleteAllBasketByPurchase(purchase.getId());
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.setBaskets(baskets);

        stompService.savePurchase(purchase);
        Purchase newPurchase = stompService.getPurchase(purchase.getId());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getPurchases().add(newPurchase);

        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteResponse);
        }
    }

    @MessageMapping("/history")
    public void historySync(HistoryResponse response) {
        History history = response.getHistory();
        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        stompService.saveHistory(history);
        History newHistory = stompService.getHistory(history.getIdBasket());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getHistories().add(newHistory);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
        }
    }

    @MessageMapping("/basket")
    public void basketSync(BasketResponse response) {
        Basket basket = response.getBasket();
        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        stompService.saveBasket(basket);
        Basket newBasket = stompService.getBasket(basket.getId());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.getBaskets().add(newBasket);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + person, accountResponse);
        }

    }

//    @MessageMapping("/target")
//    public void targetSync(TargetResponse response) {
//        Target target = response.getTarget();
//        UUID groupId = response.getGroupId();
//        List<UUID> users = stompService.getPersonsId(groupId);
//        stompService.saveTarget(target);
//        Target newTarget = stompService.getTarget(target.getId());
//        AccountResponse accountResponse = new AccountResponse();
//        accountResponse.getTargets().add(newTarget);
//        for (UUID user : users) {
//            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + user, accountResponse);
//        }
//    }

    @MessageMapping("/basket/delete")
    public void basketDeleteSync(DeleteResponse response) {
        UUID basketId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        stompService.deleteBasket(basketId);
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.getBaskets().add(basketId);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteResponse);
        }

    }

    @MessageMapping("/purchase/delete")
    public void checkDeleteSync(DeleteResponse response) {
        UUID purchaseId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        List<UUID> persons = stompService.getPersonsId(groupId);
        stompService.deletePurchase(purchaseId);
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.getPurchases().add(purchaseId);
        for (UUID person : persons) {
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + person, accountDeleteResponse);
        }

    }

    @MessageMapping("/target/delete")
    public void targetDeleteSync(DeleteResponse response) {
        UUID targetId = response.getDeleteId();
        UUID groupId = response.getGroupId();
        List<UUID> users = stompService.getPersonsId(groupId);
        stompService.deleteTarget(targetId);
        AccountDeleteResponse accountDeleteResponse = new AccountDeleteResponse();
        accountDeleteResponse.getTargets().add(targetId);
        for (UUID user : users) {
            simpMessagingTemplate.convertAndSend(SYNC_DELETE_PATH_SUBSCRIBE + user, accountDeleteResponse);
        }
    }

    public void sync(UUID personId) {
        AccountResponse accountResponse = stompService.getAccountResponse(personId);
        System.out.println("SEND SYNC");
        simpMessagingTemplate.convertAndSend(SYNC_FULL_PATH_SUBSCRIBE + personId, accountResponse);
    }
}
