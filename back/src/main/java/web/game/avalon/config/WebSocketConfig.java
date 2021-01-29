package web.game.avalon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:3000").withSockJS(); //여기서 end point 등록
        //registry.addEndpoint("/websocket").withSockJS(); //여기서 end point 등록
        System.out.println("call stomp!");

        //.setAllowedOrigins("http://localhost:3000")
        //registry.addEndpoint("/gameSocket").withSockJS();
    }
}
