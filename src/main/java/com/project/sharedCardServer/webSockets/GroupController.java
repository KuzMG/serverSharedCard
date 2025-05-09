package com.project.sharedCardServer.webSockets;

import com.google.zxing.WriterException;
import com.project.sharedCardServer.model.purchase.Purchase;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group.GroupToken;
import com.project.sharedCardServer.model.group_persons.GroupPersons;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.CreateGroupResponse;
import com.project.sharedCardServer.restController.dto.JoinInGroupResponse;
import com.project.sharedCardServer.restController.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.project.sharedCardServer.webSockets.StompController.SYNC_PATH_SUBSCRIBE;

@RestController
public class GroupController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StompService stompService;

    @PostMapping("/server/group/join")
    public ResponseEntity<String> joinGroupSync(@RequestBody JoinInGroupResponse response) {
        String token = response.getToken();
        UUID userId = response.getUserId();
        UUID groupId = stompService.checkToken(token);
        if (groupId != null) {
            if (!stompService.isPersonInGroup(userId, groupId)) {
                GroupPersons newGroupPersons = stompService.createGroupPersons(groupId, userId, GroupPersons.USER);
                List<Purchase> purchases = stompService.getPurchases(groupId);
                List<Person> people = stompService.getAllPersons(groupId);
                List<GroupPersons> groupUsers = stompService.getGroupPersons(groupId);
                Group group = stompService.getGroup(groupId);
                AccountResponse accountResponseNewUser = new AccountResponse();
                accountResponseNewUser.getPurchases().addAll(purchases);
                accountResponseNewUser.getPersons().addAll(people);
                accountResponseNewUser.getGroupPersons().addAll(groupUsers);
                accountResponseNewUser.getGroups().add(group);

                AccountResponse accountResponse = new AccountResponse();
                Person person = stompService.getUser(userId);
                accountResponse.getPersons().add(person);
                accountResponse.getGroupPersons().add(newGroupPersons);
                for (Person u : people) {
                    if (u.getId().equals(userId)) {
                        simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + u.getId(), accountResponseNewUser);
                    } else {
                        simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + u.getId(), accountResponse);
                    }
                }
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный код");
        }
    }

    @GetMapping("/server/group/token")
    public ResponseEntity<TokenResponse> getToken(
            @RequestParam("group-id") UUID groupId,
            @RequestParam("person-id") UUID personId,
            @RequestParam("password") String password
    ) {
        try {
            if (stompService.isPasswordValid(personId, password) &&
                    stompService.isPersonInGroup(personId, groupId)) {
                GroupToken token = stompService.getToken(groupId);
                TokenResponse tokenResponse = new TokenResponse(token.getToken(),token.getPic());
                return ResponseEntity.ok(tokenResponse);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/server/group/create")
    public ResponseEntity<String> createGroupSync(@RequestParam("person-id") UUID personCreatorId,
                                                  @RequestParam("password") String password,
                                                  @RequestBody CreateGroupResponse response) {
        if (stompService.isPasswordValid(personCreatorId, password)) {
            UUID userId = response.getPersonId();
            String name = response.getName();
            byte[] pic = response.getPic();
            Group group = stompService.createGroup(name, pic);
            GroupPersons groupPersons = stompService.createGroupPersons(group.getId(), userId, GroupPersons.CREATOR);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroups().add(group);
            accountResponse.getGroupPersons().add(groupPersons);
            simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + userId, accountResponse);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
