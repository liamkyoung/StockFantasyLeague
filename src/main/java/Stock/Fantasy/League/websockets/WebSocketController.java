package Stock.Fantasy.League.websockets;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class WebSocketController {
    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        return message;
    }
}
