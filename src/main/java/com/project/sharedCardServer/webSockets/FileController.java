package com.project.sharedCardServer.webSockets;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.user.User;
import com.project.sharedCardServer.restController.FileManager;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.FileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.project.sharedCardServer.restController.Authentication.HEADER_ID_USER;
import static com.project.sharedCardServer.restController.Authentication.HEADER_PASSWORD_USER;
import static com.project.sharedCardServer.webSockets.StompController.SYNC_PATH_SUBSCRIBE;
import javax.imageio.ImageIO;
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

    @GetMapping("/user/{id}")
    public byte[] getUserPic(@PathVariable("id") String name) {
        return FileManager.getUserPic(name);
    }

    @GetMapping("/qr-code/{id}")
    public byte[] getQRCodePic(@PathVariable("id") String name) {
        return FileManager.getQRCode(name);
    }

    @GetMapping("/category/{id}")
    public byte[] getCategoryPic(@PathVariable("id") String name) {
        return FileManager.getCategory(name);
    }


    @PostMapping("/group")
    public ResponseEntity saveGroupPic(@RequestHeader HttpHeaders headers,
                                       @RequestBody FileRequest fileRequest) {
        UUID idUser = UUID.fromString(Objects.requireNonNull(headers.getFirst(HEADER_ID_USER)));
        String passwordUser = headers.getFirst(HEADER_PASSWORD_USER);
        UUID idGroup = fileRequest.getId();
        if (stompService.isPasswordValid(idUser, passwordUser) && stompService.isUserInGroup(idUser,idGroup)) {
            String pic = FileManager.saveGroupPic(fileRequest.getUri(), fileRequest.getPic());
            Group group = stompService.updateGroupPic(idGroup,pic);

            List<UUID> users = stompService.getUsersId(fileRequest.getId());
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getGroups().add(group);

            for (UUID id : users) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + id, accountResponse);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity saveUserPic(@RequestHeader HttpHeaders headers,
                                      @RequestBody FileRequest fileRequest) {
        UUID userId = UUID.fromString(Objects.requireNonNull(headers.getFirst(HEADER_ID_USER)));
        String userPassword = headers.getFirst(HEADER_PASSWORD_USER);
        if (!stompService.isPasswordValid(userId, userPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            String pic = FileManager.saveUserPic(fileRequest.getUri(), fileRequest.getPic());
            User user = stompService.updateUserPic(userId,pic);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.getUsers().add(user);
            List<User> users = stompService.getUsers(userId);
            for (User value : users) {
                simpMessagingTemplate.convertAndSend(SYNC_PATH_SUBSCRIBE + value.getId(), accountResponse);
            }
            return ResponseEntity.ok().build();
        }
    }



}
