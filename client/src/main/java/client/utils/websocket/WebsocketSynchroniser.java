package client.utils.websocket;

import client.scenes.MainCtrl;
import commons.Board;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import javafx.application.Platform;
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

    private final MainCtrl mainCtrl;

    private final List<UpdateMessage> updateQueue = new ArrayList<>();

    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> syncTask;

    private final WebSocketStompClient stompClient;
    private final StompSessionHandler sessionHandler;

    private final AtomicBoolean reconnecting = new AtomicBoolean();


    public WebsocketSynchroniser(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        scheduler = Executors.newScheduledThreadPool(1);

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new WebsocketUpdateListener(this);
    }

    public void start(){
        scheduler.execute(this::connect);
        if(syncTask != null){
            syncTask.cancel(false);
        }
        syncTask = scheduler.scheduleAtFixedRate(() -> Platform.runLater(this::applyUpdates),
            1000,
            100,
            TimeUnit.MILLISECONDS);
    }

    public void stop(){
        stompClient.stop();
        scheduler.shutdown();
        syncTask.cancel(false);
    }

    public void connect(){
        // Make sure only one thread tries to connect at once.
        if(reconnecting.compareAndSet(false, true)){
            boolean disconnected = true;
            while (disconnected) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
                try {
                    stompClient.connect(SERVER, sessionHandler).get();
                    System.out.println("Connected to server via websockets");
                    disconnected = false;
                } catch (ExecutionException | CancellationException | InterruptedException e) {}
            }
            reconnecting.set(false);
        }
    }

    public void addUpdate(UpdateMessage update){
        updateQueue.add(update);
    }

    public List<UpdateMessage> poll(){
        List<UpdateMessage> updates = new ArrayList<>(updateQueue);
        updateQueue.clear();
        return updates;
    }

    public List<UpdateMessage> getUpdates(){
        return updateQueue;
    }

    public void applyUpdates(){
        List<UpdateMessage> updates = poll();
        Platform.runLater(() -> {
            for (UpdateMessage update : updates) {
                if(update instanceof BoardUpdateMessage) applyBoardUpdate(update);
            }
        });
    }

    private void applyBoardUpdate(UpdateMessage update){
        if(!(update.getObject() instanceof Board || update.getObject() == null)) return;
        Board board = (Board) update.getObject();
        switch(update.getOperation()){
            case CREATED:
                mainCtrl.addBoardToWorkspace(board);
                break;
            case DELETED:
                mainCtrl.removeFromWorkspace(update.getId());
                break;
            case UPDATED:
                mainCtrl.updateBoard(board);
                break;
        }
    }

}
