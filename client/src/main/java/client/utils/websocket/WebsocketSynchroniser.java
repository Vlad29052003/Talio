package client.utils.websocket;

import client.utils.UpdateHandler;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebsocketSynchroniser {

    private static final String SERVER = "ws://localhost:8080/ws";

    private final UpdateHandler updateHandler;

    private final List<UpdateMessage> updateQueue = new ArrayList<>();

    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> syncTask;

    private final WebSocketStompClient stompClient;
    private final StompSessionHandler sessionHandler;

    private final AtomicBoolean reconnecting = new AtomicBoolean(false);

    /**
     * Creates a new {@link WebsocketSynchroniser} object
     *
     * @param updateHandler to use for dispatching events
     */
    public WebsocketSynchroniser(UpdateHandler updateHandler){
        this.updateHandler = updateHandler;
        scheduler = Executors.newScheduledThreadPool(1);

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new WebsocketUpdateListener(this);
    }

    /**
     * Starts the synchroniser by connecting to the server and enabling update polling
     */
    public void start(){
        scheduler.execute(this::connect);
        if(syncTask != null){
            syncTask.cancel(false);
        }
        syncTask = scheduler.scheduleAtFixedRate(this::applyUpdates,
            1000,
            100,
            TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the synchroniser by closing connections and disabling update polling
     */
    public void stop(){
        reconnecting.set(false);
        stompClient.stop();
        scheduler.shutdown();
        syncTask.cancel(false);
    }

    /**
     * Tries to connect to the websocket server
     * NOTE: Method is blocking
     */
    public void connect(){
        // Make sure only one thread tries to connect at once.
        if(reconnecting.compareAndSet(false, true)){
            boolean disconnected = true;
            while (disconnected && reconnecting.get()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
                try {
                    System.out.println("Connecting to server via websockets...");
                    stompClient.connect(SERVER, sessionHandler).get();
                    System.out.println("Connected to server via websockets");
                    disconnected = false;
                } catch (ExecutionException | CancellationException | InterruptedException e) {}
            }
            reconnecting.set(false);
        }
    }

    /**
     * Queues an update to be dispatched
     * @param update to enqueue
     */
    public void addUpdate(UpdateMessage update){
        updateQueue.add(update);
    }

    /**
     * Gets and empties the {@link UpdateMessage} queue
     * @return List of {@link UpdateMessage}
     */
    public List<UpdateMessage> poll(){
        List<UpdateMessage> updates = new ArrayList<>(updateQueue);
        updateQueue.clear();
        return updates;
    }

    /**
     * Gets the {@link UpdateMessage} queue
     * @return List of {@link UpdateMessage}
     */
    public List<UpdateMessage> getUpdates(){
        return updateQueue;
    }

    /**
     * Dispatch all update events to the event handler
     */
    public void applyUpdates(){
        List<UpdateMessage> updates = poll();
        for (UpdateMessage update : updates) {
            if(update instanceof BoardUpdateMessage){
                updateHandler.dispatchBoardUpdate((BoardUpdateMessage) update);
            }
        }
    }

}
