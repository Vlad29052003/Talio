package client.utils.websocket;

import commons.messages.BoardUpdateMessage;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class WebsocketUpdateListener implements StompSessionHandler {

    private final WebsocketSynchroniser synchroniser;

    private boolean wasConnected = false;

    /**
     * Creates a new {@link WebsocketUpdateListener} object
     *
     * @param synchroniser to push incoming update messages to
     */
    public WebsocketUpdateListener(WebsocketSynchroniser synchroniser){
        this.synchroniser = synchroniser;
    }

    /**
     * Resets the handler to its initial state
     */
    public void reset(){
        wasConnected = false;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        wasConnected = true;
        session.subscribe("/topic/boards/", new StompFrameHandler() {
            public Type getPayloadType(StompHeaders headers) {
                return BoardUpdateMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                BoardUpdateMessage update = (BoardUpdateMessage) payload;
                synchroniser.addUpdate(update);
            }

        });
    }

    @Override
    public void handleException(StompSession session,
                                StompCommand command,
                                StompHeaders headers,
                                byte[] payload,
                                Throwable exception) {
        if (exception instanceof RuntimeException) {
            throw (RuntimeException) exception;
        } else if (exception instanceof Error) {
            throw (Error) exception;
        }
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        if(!session.isConnected() && wasConnected){
            synchroniser.reconnect();
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

    }

}
