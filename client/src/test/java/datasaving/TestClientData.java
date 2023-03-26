package datasaving;

import client.datasaving.ClientData;
import client.datasaving.JoinedBoardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestClientData {
    private ClientData c1;
    private ClientData c2;
    private ClientData c3;
    private JoinedBoardList l1;
    private JoinedBoardList l2;

    @BeforeEach
    public void setUp() {
        l1 = new JoinedBoardList("server1");
        l1.addBoard(1L);
        l1.addBoard(2L);
        l2 = new JoinedBoardList("server2");
        l2.addBoard(1L);

        c1 = new ClientData();
        c1.addJoinedBoardList(l1);
        c1.addJoinedBoardList(l2);

        c2 = new ClientData();
        c2.addJoinedBoardList(l2);

        c3 = new ClientData();
        c3.addJoinedBoardList(l1);
        c3.addJoinedBoardList(l2);
    }

    @Test
    public void testConstructor() {
        ClientData cl = new ClientData();
        assertNotNull(cl);
        assertEquals(cl.getServers(), new ArrayList<>());
        assertEquals(cl.getLastActiveOn(), 0);
    }

    @Test
    public void testSetLastActiveOn() {
        c1.setLastActiveOn(1);
        assertEquals(c1.getLastActiveOn(), 1);
    }

    @Test
    public void testAddJoinedBoardList() {
        ClientData c = new ClientData();
        c.addJoinedBoardList(l1);
        assertEquals(c.getServers(), List.of(l1));
    }

    @Test
    public void testGetJoinedBoardPosition() {
        assertEquals(c1.getJoinedBoardPosition(l2), 1);
        assertEquals(c1.getJoinedBoardPosition(new JoinedBoardList("")), -1);
    }

    @Test
    public void testEquals() {
        assertEquals(c1, c3);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testHashCode() {
        assertEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(c1.toString(), c3.toString());
    }
}
