package datasaving;

import client.datasaving.JoinedBoardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestJoinedBoardList {
    private JoinedBoardList test1;
    private JoinedBoardList test2;
    private JoinedBoardList test3;

    @BeforeEach
    public void setUp() {
        test1 = new JoinedBoardList("server1");
        test2 = new JoinedBoardList("server1");
        test3 = new JoinedBoardList("server2");
    }

    @Test
    public void testConstructor() {
        JoinedBoardList test = new JoinedBoardList("test");
        assertNotNull(test);
        assertEquals(test.getServer(), "test");
        assertEquals(test.getBoardIDs(), new ArrayList<>());
    }

    @Test
    public void testAddBoard() {
        test1.addBoard(1L);
        test1.addBoard(2L);
        assertEquals(test1.getBoardIDs(), List.of(1L, 2L));
    }

    @Test
    public void testRemoveBoard() {
        test1.addBoard(1L);
        test1.addBoard(2L);
        test1.removeBoard(2L);
        assertEquals(test1.getBoardIDs(), List.of(1L));
    }

    @Test
    public void testEquals() {
        test1.addBoard(1L);
        test2.addBoard(1L);
        test1.addBoard(2L);
        test2.addBoard(2L);
        test3.addBoard(1L);
        assertEquals(test1, test2);
        assertNotEquals(test1, test3);
    }

    @Test
    public void testHashCode() {
        assertEquals(test1.hashCode(), test2.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(test1.toString(), test2.toString());
    }

}
