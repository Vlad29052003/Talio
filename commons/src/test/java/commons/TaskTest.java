package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    public void setUp() {
        t1 = new Task("t1", 0, "this is t1", "#ff00ff");
        t2 = new Task("t2", 1, "this is t2", "#ff66ff");
        t3 = new Task("t1", 0, "this is t1", "#ff00ff");
    }

    @Test
    public void testEmptyConstructor() {
        Task t = new Task();
        assertNotNull(t);
    }

    @Test
    public void testNonEmptyConstructor() {
        Task t = new Task("t", 5, "this is t", "#112233");
        assertNotNull(t);
        assertEquals(t.name, "t");
        assertEquals(t.index, 5);
        assertEquals(t.description, "this is t");
        assertEquals(t.subtasks, new ArrayList<String>());
        assertEquals(t.color, "#112233");
    }

    @Test
    public void testGetTaskList() {
        TaskList l = new TaskList("a");
        t1.setTaskList(l);
        assertEquals(t1.getTaskList(), l);
    }

    @Test
    public void testSetTaskList() {
        TaskList tl = new TaskList("tl");
        t1.setTaskList(tl);
        assertEquals(tl, t1.list);
    }

    @Test
    public void testSetTaskListNotNullList() {
        TaskList tl1 = new TaskList("tl1");
        TaskList tl2 = new TaskList("tl2");
        t1.setTaskList(tl1);
        t1.setTaskList(tl2);
        assertEquals(tl2, t1.list);
    }

    @Test
    public void testAddSubtask() {
        t1.addSubTask("s1");
        assertFalse(t1.addSubTask("s1"));
        List<String> expected = new ArrayList<>();
        // a zero or one is appended to the name
        // to show whether the subtask is completed.
        expected.add("s10");
        assertEquals(expected, t1.subtasks);
    }

    @Test
    public void testAddExistingSubtask() {
        t1.addSubTask("s1");
        assertFalse(t1.addSubTask("s1"));
    }

    @Test
    public void testRemoveExistingSubtask() {
        t1.addSubTask("s1");
        List<String> expected = new ArrayList<>();
        assertTrue(t1.removeSubTask("s1"));
        assertEquals(t1.subtasks, expected);
    }

    @Test
    public void testRemoveNonExistingSubtask() {
        t1.addSubTask("s1");
        List<String> expected = new ArrayList<>();
        expected.add("s10");
        assertFalse(t1.removeSubTask("s2"));
        assertEquals(t1.subtasks, expected);
    }

    @Test
    public void testSetSubtask() {
        t1.addSubTask("s1");
        t1.setSubTask("s1", true);
        assertTrue(t1.subtasks.contains("s11"));
    }

    @Test
    public void testCalculateProgress() {
        t1.addSubTask("s1");
        t1.setSubTask("s1", true);
        t1.addSubTask("s2");
        assertEquals(t1.calculateProgress(), 0.5);
    }

    @Test
    public void testCalculateProgressEmptyList() {
        assertEquals(t1.calculateProgress(), 1.0);
    }

    @Test
    public void testCompletedSubtasks() {
        t1.addSubTask("s1");
        t1.setSubTask("s1", true);
        assertEquals(t1.completedSubtasks(), 1);
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
    public void testToString() {
        assertNotNull(t1.toString());
    }

    @Test
    public void testCompareTo() {
        Task t1 = new Task("t1", 1, "");
        Task t2 = new Task("t2", 2, "");

        assertEquals(t1.compareTo(t2), -1);
    }

    @Test
    public void testCompareToNull() {
        Task t1 = new Task("t1", 1, "");

        assertThrows(NullPointerException.class, () -> {
            t1.compareTo(null);
        });
    }

    @Test
    public void testSetSubTask(){
        List<String> expect = new ArrayList<>();
        expect.add("sub10");
        t1.addSubTask("sub1");
        assertEquals(t1.subtasks, expect);

        expect.clear();
        expect.add("sub11");
        t1.setSubTask("sub1", true);
        assertEquals(t1.subtasks, expect);

        t1.setSubTask("sub1", true);
        assertEquals(t1.subtasks, expect);
    }

    @Test
    public void testCalculateProgress2(){
        assertEquals(1.0, t1.calculateProgress());
        
        t1.addSubTask("sub1");
        t1.addSubTask("sub2");
        t1.setSubTask("sub1", true);
        assertEquals(0.5,t1.calculateProgress());
    }

    @Test
    public void testCompletedSubtasks2(){
        assertEquals(0, t1.completedSubtasks());

        t1.addSubTask("sub1");
        t1.addSubTask("sub2");
        t1.addSubTask("sub3");
        t1.setSubTask("sub1", true);
        t1.setSubTask("sub2", true);
        assertEquals(2,t1.completedSubtasks());
    }

}
