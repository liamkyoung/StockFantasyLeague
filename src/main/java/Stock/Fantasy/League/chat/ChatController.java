package Stock.Fantasy.League.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Example WebSocket functionality
@Slf4j
@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        log.info("Message received: {}", message.getContent());
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor
    ) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        log.info("Adding user: {}", message.getSender());
        return message;
    }
}
