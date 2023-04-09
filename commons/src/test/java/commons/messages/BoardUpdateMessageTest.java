package commons.messages;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoardUpdateMessageTest {
    public BoardUpdateMessage mess;
    public Board board1;
    public Board board2;

    @BeforeEach
    public void SetUp(){
        board1 = new Board("1");
        board2 = new Board("2");
        mess = new BoardUpdateMessage(board1.id, board1,
                UpdateMessage.Operation.DELETED);
    }

    @Test
    public void TestConstructor(){
        BoardUpdateMessage mess1 = new BoardUpdateMessage(board1.id, board1,
                UpdateMessage.Operation.CREATED);
        assertEquals(mess1.getId(), board1.id);
        assertEquals(mess1.board, board1);
        assertEquals(mess1.getOperation(), UpdateMessage.Operation.CREATED);

        BoardUpdateMessage mess2 = new BoardUpdateMessage();
        assertNotNull(mess2);
    }

    @Test
    public void TestSetGetObject(){
        mess.setObject(board2);
        mess.setObject(new Object());
        assertEquals((Board) mess.getObject(), board2);
    }

    @Test
    public void TestToString(){
        assertNotNull(mess.toString());
    }
}
