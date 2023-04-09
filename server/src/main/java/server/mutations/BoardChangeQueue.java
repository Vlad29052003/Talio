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

    /**
     * Creates a new {@link BoardChangeQueue} object
     */
    public BoardChangeQueue(){
        boardQueue = new ArrayList<>();
    }

    /**
     * Adds a new deletion update to the queue
     *
     * @param id of the board to be deleted
     */
    public void addDeleted(long id){
        UpdateMessage update = new BoardUpdateMessage(id, null, UpdateMessage.Operation.DELETED);
        boardQueue.add(update);
    }

    /**
     * Adds a new creation update to the queue
     *
     * @param id of the created board
     * @param board the newly created board
     */
    public void addCreated(long id, Board board){
        UpdateMessage update = new BoardUpdateMessage(id, board, UpdateMessage.Operation.CREATED);
        boardQueue.add(update);
    }

    /**
     * Adds a new change update to the queue
     *
     * @param id of the changes board
     * @param board the board with changes
     */
    public void addChanged(long id, Board board){
        UpdateMessage update = new BoardUpdateMessage(id, board, UpdateMessage.Operation.UPDATED);
        boardQueue.add(update);
    }

    /**
     * Gets and empties the queue of updates
     * @return A list of {@link UpdateMessage}
     */
    public List<UpdateMessage> pollUpdates(){
        List<UpdateMessage> changes = new ArrayList<>(boardQueue);
        boardQueue.clear();
        return changes;
    }

    /**
     * @return The number of updates in the queue
     */
    public int size(){
        return boardQueue.size();
    }

}
