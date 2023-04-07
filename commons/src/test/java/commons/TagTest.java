package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagTest {
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Task t1;
    private Task t2;

    @BeforeEach
    public void setUp() {
        t1 = new Task("task1", 0, "");
        t2 = new Task("task2", 0, "");
        tag1 = new Tag("tag1", "red");
        tag2 = new Tag("tag1", "red");
        tag3 = new Tag("tag3", "red");
    }

    @Test
    public void testEmptyConstructor() {
        Tag t = new Tag();
        assertNotNull(t);
    }

    @Test
    public void testConstructor() {
        Tag t = new Tag("t", "b");
        assertEquals(t.name, "t");
        assertEquals(t.color, "b");
        assertEquals(t.tasks, new HashSet<>());
    }

    @Test
    public void testApplyTo() {
        tag1.applyTo(t1);
        tag1.applyTo(t2);
        assertEquals(tag1.tasks, Set.of(t1, t2));
    }

    @Test
    public void testRemoveFrom() {
        tag1.applyTo(t1);
        tag1.applyTo(t2);
        assertEquals(tag1.tasks, Set.of(t1, t2));

        tag1.removeFrom(t1);
        assertEquals(tag1.tasks, Set.of(t2));
    }

    @Test
    public void testEquals() {
        assertEquals(tag1, tag2);
        assertNotEquals(tag1, tag3);
    }

    @Test
    public void testHashCode() {
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(tag1.toString());
    }

    @Test
    public void testCompareTo() {
        tag1.id = 0L;
        tag2.id = 5L;
        assertEquals(tag1.compareTo(tag2), -1);
    }

    @Test
    public void testInvalidCompareTo() {
        assertThrows(NullPointerException.class, () -> {
            tag1.compareTo(null);
        });
    }
}
