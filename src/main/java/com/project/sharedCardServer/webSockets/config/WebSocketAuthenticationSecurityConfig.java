package com.project.sharedCardServer.webSockets.config;

import com.project.sharedCardServer.webSockets.StompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.UUID;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationSecurityConfig implements WebSocketMessageBrokerConfigurer {
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
    private static final String HEADER_ID_PERSON = "id-person";
    private static final String HEADER_PASSWORD_PERSON = "password-person";
    @Autowired
    private StompService stompService;
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        // Endpoints are already registered on WebSocketConfig, no need to add more.
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) throws AuthenticationException {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                    final MultiValueMap<String, String> nativeHeaders = (MultiValueMap<String, String>) headerAccessor.getHeader(StompHeaderAccessor.NATIVE_HEADERS);

                    UUID personId = UUID.fromString(nativeHeaders.get(HEADER_ID_PERSON).get(0));
                    String password = nativeHeaders.get(HEADER_PASSWORD_PERSON).get(0);
                    UsernamePasswordAuthenticationToken user = null;
                        user = stompService.getAuthenticatedOrFail(personId, password);
                        accessor.setUser(user);

                }
                return message;
            }
        });
    }

}