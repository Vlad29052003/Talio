package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskTest {
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    public void setUp() {
        t1 = new Task("t1", 0, "this is t1");
        t2 = new Task("t2", 1, "this is t2");
        t3 = new Task("t1", 0, "this is t1");
    }

    @Test
    public void testConstructor() {
        Task t = new Task("t", 5, "this is t");
        assertNotNull(t);
        assertEquals(t.name, "t");
        assertEquals(t.index, 5);
        assertEquals(t.description, "this is t");
        assertEquals(t.subtasks, new ArrayList<String>());
    }

    @Test
    public void testEquals() {
        assertEquals(t1, t3);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(t1, t2);
    }

    @Test
    public void testEqualHashCode() {
        assertEquals(t1.hashCode(), t3.hashCode());
    }

    @Test
    public void testAddSubtask() {
        t1.addSubTask("s1");
        java.util.List<String> expected = new ArrayList<>();
        expected.add("s1");
        assertEquals(expected, t1.subtasks);
    }

    @Test
    public void testToString() {
        assertEquals(t1.toString(), "this is t1,0,0,<null>,t1,[]");
    }
}
