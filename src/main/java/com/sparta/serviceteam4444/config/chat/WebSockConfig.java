package com.sparta.serviceteam4444.config.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
                .withSockJS();
    }

//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(webSocketHandler, "/ws/chat")
//                .setAllowedOrigins("*");
//    }

    @EventListener
    public void connectEvent(SessionConnectEvent sessionConnectEvent){
        log.info(String.valueOf(sessionConnectEvent));
        log.info("연결 성공");
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent){
        log.info(sessionDisconnectEvent.getSessionId());
        log.info("연결 해제");
    }
}