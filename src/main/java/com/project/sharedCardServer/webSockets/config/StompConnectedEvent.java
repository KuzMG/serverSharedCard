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
        String destination = (String) event.getMessage().getHeaders().get("simpDestination");
        if (destination != null && destination.startsWith("/topic/full.")) {
            UUID personId = UUID.fromString(event.getUser().getName());
            controller.sync(personId);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
