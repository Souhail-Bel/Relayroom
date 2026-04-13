package com.kld_sou.relayroom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  @Value("${allowed.origins}") private String allowedOrigins;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/ws")
        // do note that setAllowedOrigin breaks SockJS
        .setAllowedOriginPatterns(allowedOrigins)
        .withSockJS(); // fallback long-polling when WS fails

    // Note when SockJS is enabled and origins are restricted, transport types
    // that do not allow to check request origin (Iframe based transports) are
    // disabled. As a consequence, IE 6 to 9 are not supported when origins are
    // restricted.
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableSimpleBroker("/topic");
  }
}
