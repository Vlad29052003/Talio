package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private Board board1;
    private Board board2;
    private Board board3;

    private TaskList list1;
    private TaskList list2;
    private TaskList list3;
    private TaskList list4;

    @BeforeEach
    public void setUp() {
        board1 = new Board("board1", "white", "black");
        board2 = new Board("board2", "black", "white");
        board3 = new Board("board1", "white", "black");
        list1 = new TaskList("1");
        list2 = new TaskList("2");
        list3 = null;
        list4 = new TaskList("4");
    }

    @Test
    public void testConstructor() {
        Board nullboard = new Board();
        assertNotNull(nullboard);

        Board board = new Board("board", "white", "black", "pass",true);
        assertNotNull(board);
        assertEquals(board.name, "board");
        assertEquals(board.backgroundColor, "white");
        assertEquals(board.fontColor, "black");
        assertEquals(board.password, "pass");
        assertTrue(board.RWpermission);
    }

    @Test
    public void testEquals() {

        assertEquals(board1, board3);
        assertNotEquals(board1, board2);
    }

    @Test
    public void testEqualHashCode() {

        assertEquals(board1.hashCode(), board3.hashCode());
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    public void testAddList() {
        board1.addTaskList(list3);
        assertEquals(board1,board3);

        board1.addTaskList(list1);
        board2.addTaskList(list2);
        assertNotEquals(board1.lists,board2.lists);
        assertTrue(board1.lists.contains(list1));
        assertTrue((board2.lists.contains(list2)));

        board2.addTaskList(list1);
        assertTrue(board1.lists.isEmpty());
        assertEquals(2, board2.lists.size());

    }

    @Test
    public void testRemoveList() {
        board1.addTaskList(list1);
        board1.addTaskList(list4);
        board2.addTaskList(list2);

        board2.removeTaskList(null);
        assertEquals(board2.lists.size(),1);

        board2.removeTaskList(list2);
        assertTrue(board2.lists.isEmpty());

        board1.removeTaskList(list1);
        assertTrue(board1.lists.contains(list4));
        assertFalse((board1.lists.contains(list1)));
        assertEquals(board1.lists.size(),1);

    }

    @Test
    public void testToString() {
        assertNotNull(board1.toString());
    }

}
