package com.project.sharedCardServer.webSockets;

import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.restController.FileManager;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.FileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.project.sharedCardServer.restController.Authentication.HEADER_ID_PERSON;
import static com.project.sharedCardServer.restController.Authentication.HEADER_PASSWORD;
import static com.project.sharedCardServer.webSockets.StompController.SYNC_PATH_SUBSCRIBE;
@RestController()
@RequestMapping("/pic")
public class FileController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StompService stompService;

    @GetMapping("/group/{id}")
    public byte[] getGroupPic(@PathVariable("id") String name) {
        return FileManager.getGroupPic(name);
    }

    @GetMapping("/person/{id}")
    public byte[] getPersonPic(@PathVariable("id") String name) {
        return FileManager.getPersonPic(name);
    }

    @GetMapping("/qr-code/{id}")
    public byte[] getQRCodePic(@PathVariable("id") String name) {
        return FileManager.getQRCode(name);
    }

    @GetMapping("/category/{id}")
    public byte[] getCategoryPic(@PathVariable("id") String name) {
        return FileManager.getCategory(name);
    }




    @GetMapping("/recipe/{id}")
    public byte[] getRecipePic(@PathVariable("id") String name) {
        return FileManager.getRecipe(name);
    }

    @PostMapping("/group")
    public ResponseEntity saveGroupPic(@RequestHeader HttpHeaders headers,
                                       @RequestBody FileRequest fileRequest) {
        UUID personId = UUID.fromString(Objects.requireNonNull(headers.getFirst(HEADER_ID_PERSON)));
        String passwordPerson = headers.getFirst(HEADER_PASSWORD);
        UUID groupId = fileRequest.getId();
        if (stompService.isPasswordValid(personId, passwordPerson) && stompService.isPersonInGroup(personId,groupId)) {
            String pic = FileManager.saveGroupPic(fileRequest.getUri(), fileRequest.getPic());
            Group group = stompService.updateGroupPic(groupId,pic);

            List<UUID> persons = stompService.getPersonsId(fileRequest.getId());
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroups().add(group);

            for (UUID id : persons) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + id, accountResponse);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/person")
    public ResponseEntity savePersonPic(@RequestHeader HttpHeaders headers,
                                        @RequestBody FileRequest fileRequest) {
        UUID personId = UUID.fromString(Objects.requireNonNull(headers.getFirst(HEADER_ID_PERSON)));
        String personPassword = headers.getFirst(HEADER_PASSWORD);
        if (!stompService.isPasswordValid(personId, personPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            String pic = FileManager.savePersonPic(fileRequest.getUri(), fileRequest.getPic());
            Person person = stompService.updatePersonPic(personId,pic);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getPersons().add(person);
            List<Person> persons = stompService.getPersons(personId);
            for (Person p : persons) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + p.getId(), accountResponse);
            }
            return ResponseEntity.ok().build();
        }
    }



}
