package commons;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void TestCreateTaskList() {
        TaskList list1 = new TaskList(0, "Hello, World", 4, new HashSet<>());
        TaskList list2 = new TaskList(0, "Hello, World", 4);
        assertEquals(list1, list2);
    }
}
