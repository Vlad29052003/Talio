package server.websocket;

import commons.messages.UpdateMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import server.mutations.BoardChangeQueue;

import java.util.List;

@Controller
public class WebsocketController {

    private final SimpMessagingTemplate template;

    public BoardChangeQueue changes;

    /**
     * Creates a new {@link WebsocketController} object
     * @param changes to use
     * @param template to use
     */
    public WebsocketController(BoardChangeQueue changes, SimpMessagingTemplate template){
        this.changes = changes;
        this.template = template;
    }

    /**
     * Polls update queue and broadcasts them to all clients
     */
    @Scheduled(fixedRate = 100)
    public void pushBoardUpdate() {
        List<UpdateMessage> ch = changes.pollUpdates();
        for(UpdateMessage update : ch){
            template.convertAndSend("/topic/boards/", update);
        }
    }

}
