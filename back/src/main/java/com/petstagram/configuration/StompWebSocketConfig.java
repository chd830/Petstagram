//package com.petstagram.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@EnableWebSocketMessageBroker
//@Configuration
//public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//   // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
//   public void configureMessageBroker(MessageBrokerRegistry config) {
//   	// 메세지를 구독하는 요청
//       config.enableSimpleBroker("/sub");
////        메세지 발행하는 요청
//       config.setApplicationDestinationPrefixes("/pub");
//   }
//
//// stomp websocket의 연결 endpoint /ws-stomp(ws://localhost:8080/ws-stomp)
//   @Override
//   public void registerStompEndpoints(StompEndpointRegistry registry) {
//   // connection을 맺을때 CORS 허용합니다.
//       registry.addEndpoint("/").setAllowedOrigins("*").withSockJS();
//   }
//}