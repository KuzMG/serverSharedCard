package com.project.sharedCardServer.webSockets.config;

import com.project.sharedCardServer.webSockets.StompController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.UUID;

@Component
public class StompConnectedEvent implements ApplicationListener<SessionSubscribeEvent> {


    @Autowired
    private StompController controller;



    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        System.out.println("Client connected.");
        UUID idUser = UUID.fromString(event.getUser().getName());
    
        controller.sync(idUser);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
