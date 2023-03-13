package com.sparta.serviceteam4444.config.chat;

import com.sparta.serviceteam4444.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker //websocket 서버를 사용한다는 설정
@RequiredArgsConstructor
@Slf4j
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub"); // 핸들러로 라우팅하여 해당 주제에 가입한 모든 클라이언트에게 메시지를 발송
        config.setApplicationDestinationPrefixes("/pub"); // 메시지 헨들러로 라우팅
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*") // 클라이언트에서 websocket에 접속하는 endpoint 등록
                .withSockJS(); // 브라우저에서 websocket을 지원하지 않을 경우 fallback 옵션을 활성화
    }
    @Override
    // 메세지를 받았을때 최초에 stompHandler 가 인터셉트 하도록 설정
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}