package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {


    private Board board1;
    private Board board2;
    private Board board3;

    private TaskList list1;
    private TaskList list2;
    private TaskList list3;
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;

    @BeforeEach
    public void setUp() {
        board1 = new Board("board1", "White");
        board2 = new Board("board2", "White");
        board3 = null;
        list1 = new TaskList("1", 1);
        list2 = new TaskList("2", 2);
        list3 = new TaskList("1",1);


        t1 = new Task("t1", 0, "this is t1");
        t2 = new Task("t2", 1, "this is t2");
        t3 = null;
        t4 = new Task("t1", 0, "this is t1");
    }

    @Test
    public void testConstructor() {
        TaskList nulllist = new TaskList();
        assertNotNull(nulllist);

        TaskList list = new TaskList("list", 20);
        assertNotNull(list);
        assertEquals(list.name, "list");
        assertEquals(list.index, 20);
        assertEquals(list.tasks, new HashSet<>());
    }

    @Test
    public void testEquals() {

        assertEquals(list1, list3);
        assertNotEquals(list1, list2);
    }

    @Test
    public void testEqualHashCode() {

        assertEquals(list1.hashCode(), list3.hashCode());
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void testSetBoard() {
        list1.setBoard(board3);
        assertEquals(list1,list3);

        list1.setBoard(board1);
        list2.setBoard(board1);
        assertEquals(list1.board,list2.board);

        list3.setBoard(board2);
        assertNotEquals(list1.board,list3.board);

        list1.setBoard(board2);
        assertEquals(list1.board,list3.board);
    }

    @Test
    public void testAddTask() {
        list1.addTask(t3);
        assertEquals(list1,list3);

        list1.addTask(t1);
        list2.addTask(t1);
        assertNotEquals(list1.tasks,list2.tasks);
        assertTrue(list1.tasks.isEmpty());

        list3.addTask(t4);
        assertEquals(t4.list, list3);
        assertTrue(list3.tasks.contains(t4));
    }

    @Test
    public void testRemoveTask() {
        list1.addTask(t1);

        list1.removeTask(t3);
        list1.removeTask(t2);
        assertTrue(list1.tasks.contains(t1));

        list1.removeTask(t1);
        assertTrue(list1.tasks.isEmpty());
    }

    @Test
    public void testToString() {
        assertNotNull(list1.toString());
    }

}
