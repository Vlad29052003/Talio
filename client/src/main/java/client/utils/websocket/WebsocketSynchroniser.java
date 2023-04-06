package client.utils.websocket;

import client.utils.ServerURL;
import client.utils.UpdateHandler;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebsocketSynchroniser {

    private ServerURL url;

    private final UpdateHandler updateHandler;

    private final Queue<UpdateMessage> updateQueue = new LinkedList<>();

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> syncTask;

    private WebSocketStompClient stompClient;
    private WebsocketUpdateListener sessionHandler;

    private StompSession stompSession;

    private final AtomicBoolean reconnecting = new AtomicBoolean(false);

    /**
     * Creates a new {@link WebsocketSynchroniser} object
     *
     * @param updateHandler to use for dispatching events
     */
    public WebsocketSynchroniser(UpdateHandler updateHandler){
        this.updateHandler = updateHandler;
    }

    /**
     * Starts the synchroniser by connecting to the server and enabling update polling
     */
    public void start(){
        stop();

        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new WebsocketUpdateListener(this);

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.execute(this::connect);
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
        if(stompSession != null && stompSession.isConnected()) stompSession.disconnect();
        if(stompClient != null) stompClient.stop();
        if(sessionHandler != null) sessionHandler.reset();
        if(syncTask != null) syncTask.cancel(false);
        if(scheduler != null && !scheduler.isShutdown()){
            scheduler.shutdownNow();
        }
    }

    /**
     * Tries to connect to the websocket server
     * NOTE: Method is blocking
     */
    public void connect(){
        if(stompClient == null) return;
        if(sessionHandler == null) return;
        // Make sure only one thread tries to connect at once.
        if(reconnecting.compareAndSet(false, true)){
            stompSession = null;
            try {
                System.out.println("Connecting to server via websockets...");
                stompSession = stompClient.connect(getServerURL(), sessionHandler).get();
                System.out.println("Connected to server via websockets");
            } catch (ExecutionException | CancellationException | InterruptedException e) {}
            reconnecting.set(false);
        }
    }

    /**
     * @return The server URL with protocol and path
     */
    public String getServerURL(){
        return "ws://" + url.toString() + "/ws";
    }

    /**
     * Tries to reconnect to a new server
     *
     * @param url address of the server.
     */
    public void switchServer(ServerURL url){
        stop();
        this.url = url;
        start();
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
    public Queue<UpdateMessage> poll(){
        Queue<UpdateMessage> updates = new LinkedList<>(updateQueue);
        updateQueue.clear();
        return updates;
    }

    /**
     * Gets the {@link UpdateMessage} queue
     * @return Queue of {@link UpdateMessage}
     */
    public Queue<UpdateMessage> getUpdates(){
        return updateQueue;
    }

    /**
     * Dispatch all update events to the event handler
     */
    public void applyUpdates(){
        Queue<UpdateMessage> updates = poll();
        for (UpdateMessage update : updates) {
            if(update instanceof BoardUpdateMessage){
                updateHandler.dispatchBoardUpdate((BoardUpdateMessage) update);
            }
        }
    }

    /**
     * Stops the synchroniser and dispatches a disconnect event
     */
    public void reconnect() {
        stop();
        updateHandler.onDisconnect();
    }
}
