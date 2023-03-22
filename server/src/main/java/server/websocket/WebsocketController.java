package server.websocket;

import commons.messages.BoardUpdate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import server.api.BoardChangeQueue;

import java.util.List;

@Controller
public class WebsocketController {

    private SimpMessagingTemplate template;

    public BoardChangeQueue changes;

    public WebsocketController(BoardChangeQueue changes, SimpMessagingTemplate template){
        this.changes = changes;
        this.template = template;
    }

    @Scheduled(fixedRate = 1000)
    public void pushBoardUpdate() {
        List<BoardUpdate> ch = changes.pollUpdates();
        for(BoardUpdate update : ch){
            template.convertAndSend("/topic/boards/", update);
        }
    }

}
