package client.utils.websocket;

import client.utils.UpdateHandler;
import commons.Board;
import commons.messages.BoardUpdateMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static commons.messages.UpdateMessage.Operation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebsocketSynchroniserTest {

    private WebsocketSynchroniser sut;
    private MyTestUpdateHandler updateHandler;


    private CompletableFuture<Board> created;
    private CompletableFuture<Board> updated;
    private CompletableFuture<Long> deleted;

    @BeforeEach
    public void setup(){
        created = new CompletableFuture<>();
        updated = new CompletableFuture<>();
        deleted = new CompletableFuture<>();

        updateHandler = new MyTestUpdateHandler();
        sut = new WebsocketSynchroniser(updateHandler);
    }

    @Test
    public void dryCreationTest() {
        Board test = getDummyBoard();
        BoardUpdateMessage update = new BoardUpdateMessage(1, test, Operation.CREATED);
        sut.addUpdate(update);

        assertEquals(1, sut.getUpdates().size());
        assertEquals(sut.getUpdates().peek(), update);

        sut.applyUpdates();
        Board board = created.getNow(null);
        assertEquals(test, board);
    }

    @Test
    public void dryUpdateTest() {
        Board test = getDummyBoard();
        BoardUpdateMessage update = new BoardUpdateMessage(1, test, Operation.UPDATED);
        sut.addUpdate(update);

        assertEquals(1, sut.getUpdates().size());
        assertEquals(sut.getUpdates().peek(), update);

        sut.applyUpdates();
        Board board = updated.getNow(null);
        assertEquals(test, board);
    }

    @Test
    public void dryDeletionTest() {
        long id = 1239;
        BoardUpdateMessage update = new BoardUpdateMessage(id, null, Operation.DELETED);
        sut.addUpdate(update);

        assertEquals(1, sut.getUpdates().size());
        assertEquals(sut.getUpdates().peek(), update);

        sut.applyUpdates();
        Long result_id = deleted.getNow(null);
        assertEquals(id, result_id);
    }

    @Test
    public void dryAllTest() {
        Board test = getDummyBoard();
        BoardUpdateMessage update1 = new BoardUpdateMessage(1, test, Operation.CREATED);
        BoardUpdateMessage update2 = new BoardUpdateMessage(1, test, Operation.UPDATED);
        BoardUpdateMessage update3 = new BoardUpdateMessage(1, test, Operation.DELETED);
        sut.addUpdate(update1);
        sut.addUpdate(update2);
        sut.addUpdate(update3);
        assertEquals(3, sut.getUpdates().size());
        sut.applyUpdates();
        assertEquals(0, sut.getUpdates().size());
    }

    @Test
    public void concurrentConnectionTest() throws InterruptedException {
        sut.start();
        Executors.newSingleThreadExecutor().execute(sut::connect);

        Thread.sleep(100);
        sut.stop();
    }

    @Test
    public void doubleStartTest() {
        sut.start();
        sut.start();
    }

    private Board getDummyBoard(){
        return new Board("Board1", "black", "black", "password");
    }

    private class MyTestUpdateHandler extends UpdateHandler {

        @Override
        public void onBoardCreated(Board board) {
            created.complete(board);
        }

        @Override
        public void onBoardDeleted(long id) {
            deleted.complete(id);
        }

        @Override
        public void onBoardUpdated(Board board) {
            updated.complete(board);
        }

        @Override
        public void onDisconnect() {

        }
    }
    
}
