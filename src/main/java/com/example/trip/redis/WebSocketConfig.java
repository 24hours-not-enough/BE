package com.example.trip.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 서버 -> 클라이언트(클라이언트가 subscribe 할 때)
        config.enableSimpleBroker("/topic", "/queue");
        // 클라이언트 -> 서버
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트에서 websocket을 연결할 api를 설정
        // 채팅 포인트
        registry.addEndpoint("/endpoint")
                .setAllowedOrigins("https://triplan.co.kr")
                .withSockJS();
        // 알림 포인트
        registry.addEndpoint("/alarmpoint")
                .setAllowedOrigins("https://triplan.co.kr")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
        channelRegistration.interceptors(stompHandler);
    }


}

