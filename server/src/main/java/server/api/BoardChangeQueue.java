package server.api;

import commons.Board;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class BoardChangeQueue {

    private final HashMap<Long, UpdateMessage> boardQueue;

    public BoardChangeQueue(){
        boardQueue = new HashMap<>();
    }

    public void addDeleted(long id){
        UpdateMessage update = new BoardUpdateMessage(id, null, UpdateMessage.Operation.DELETED);
        boardQueue.put(id, update);
    }

    public void addCreated(long id, Board b){
        UpdateMessage update = new BoardUpdateMessage(id, b, UpdateMessage.Operation.CREATED);
        boardQueue.put(id, update);
    }

    public void addChanged(long id, Board b){
        UpdateMessage update = new BoardUpdateMessage(id, b, UpdateMessage.Operation.UPDATED);
        boardQueue.put(id, update);
    }

    public List<UpdateMessage> pollUpdates(){
        List<UpdateMessage> changes = new ArrayList<>(boardQueue.values());
        boardQueue.clear();
        return changes;
    }

    public int size(){
        return boardQueue.size();
    }

}
