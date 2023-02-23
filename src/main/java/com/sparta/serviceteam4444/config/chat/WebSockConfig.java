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
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSockConfig implements WebSocketConfigurer {

    private final WebSockChatHandler webSockChatHandler;

//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config){
//        config.enableSimpleBroker("/sub"); // 핸들러로 라우팅하여 해당 주제에 가입한 모든 클라이언트에게 메시지를 발송
//        config.setApplicationDestinationPrefixes("/pub"); // 메시지 헨들러로 라우팅
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry){
//        log.info(registry.toString());
//        registry.addEndpoint("/ws").setAllowedOriginPatterns("*") // 클라이언트에서 websocket에 접속하는 endpoint 등록
//                .withSockJS(); // 브라우저에서 websocket을 지원하지 않을 경우 fallback 옵션을 활성화
//    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(webSockChatHandler, "/ws/chat")
                .setAllowedOriginPatterns("*");
    }

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