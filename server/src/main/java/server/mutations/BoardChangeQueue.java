package server.mutations;

import commons.Board;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardChangeQueue {

    private final List<UpdateMessage> boardQueue;

    public BoardChangeQueue(){
        boardQueue = new ArrayList<>();
    }

    public void addDeleted(long id){
        UpdateMessage update = new BoardUpdateMessage(id, null, UpdateMessage.Operation.DELETED);
        boardQueue.add(update);
    }

    public void addCreated(long id, Board b){
        UpdateMessage update = new BoardUpdateMessage(id, b, UpdateMessage.Operation.CREATED);
        boardQueue.add(update);
    }

    public void addChanged(long id, Board b){
        UpdateMessage update = new BoardUpdateMessage(id, b, UpdateMessage.Operation.UPDATED);
        boardQueue.add(update);
    }

    public List<UpdateMessage> pollUpdates(){
        List<UpdateMessage> changes = new ArrayList<>(boardQueue);
        boardQueue.clear();
        return changes;
    }

    public int size(){
        return boardQueue.size();
    }

}
