package server.websocket;

import commons.Board;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @MessageMapping("/broadcastboard")
    @SendTo("/topic/boards")
    public Board send() throws Exception {
        System.out.println("Message");
        return new Board("board", "Black");
    }

}
