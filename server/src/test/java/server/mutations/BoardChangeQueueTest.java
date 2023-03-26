package server.mutations;

import commons.Board;
import commons.messages.UpdateMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardChangeQueueTest {

    private BoardChangeQueue sut;


    @BeforeEach
    public void setup(){
        sut = new BoardChangeQueue();
    }

    @Test
    public void testCreation(){
        Board board = getDummyBoard();
        sut.addCreated(1, board);
        assertEquals(1, sut.size());
        List<UpdateMessage> updates = sut.pollUpdates();
        assertEquals(1, updates.size());
        UpdateMessage update = updates.get(0);
        assertEquals(UpdateMessage.Operation.CREATED, update.getOperation());
        assertEquals(board, update.getObject());
    }

    @Test
    public void testUpdate(){
        Board board = getDummyBoard();
        sut.addCreated(1, board);
        sut.addChanged(1, board);
        List<UpdateMessage> updates = sut.pollUpdates();
        assertEquals(2, updates.size());
        UpdateMessage update = updates.get(1);
        assertEquals(UpdateMessage.Operation.UPDATED, update.getOperation());
        assertEquals(board, update.getObject());
    }

    @Test
    public void testDelete(){
        Board board = getDummyBoard();
        sut.addCreated(1, board);
        sut.addDeleted(1);
        assertEquals(2, sut.size());
        List<UpdateMessage> updates = sut.pollUpdates();
        assertEquals(2, updates.size());
        UpdateMessage update = updates.get(1);
        assertEquals(UpdateMessage.Operation.DELETED, update.getOperation());
        assertNull(update.getObject());
    }

    @Test
    public void testMultipleOrder(){
        Board board = getDummyBoard();
        sut.addCreated(1, board);
        sut.addChanged(1, board);
        sut.addDeleted(1);
        sut.addChanged(1, board);
        sut.addCreated(1, board);
        List<UpdateMessage> updates = sut.pollUpdates();
        assertEquals(5, updates.size());
        assertEquals(UpdateMessage.Operation.CREATED, updates.get(0).getOperation());
        assertEquals(UpdateMessage.Operation.UPDATED, updates.get(1).getOperation());
        assertEquals(UpdateMessage.Operation.DELETED, updates.get(2).getOperation());
        assertEquals(UpdateMessage.Operation.UPDATED, updates.get(3).getOperation());
        assertEquals(UpdateMessage.Operation.CREATED, updates.get(4).getOperation());
    }

    private Board getDummyBoard(){
        return new Board("Board1", "Black", "password", false);
    }

}
