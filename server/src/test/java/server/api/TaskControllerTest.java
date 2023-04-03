package server.api;

import commons.Board;
import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.mutations.BoardChangeQueue;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskControllerTest {
    private TaskListTestRepository listRepo;
    private TestTaskRepository taskRepo;
    private TaskController taskController;

    private List<TaskList> lists;

    private List<Task> tasks;

    @BeforeEach
    public void setup() {
        lists = new ArrayList<>();
        tasks = new ArrayList<>();
        listRepo = new TaskListTestRepository();
        taskRepo = new TestTaskRepository();
        taskController = new TaskController(taskRepo, listRepo);
        Board parent = new Board("Board1", "color");
        TaskList l1 = new TaskList("list1");
        l1.id = 1L;
        parent.addTaskList(l1);
        TaskList l2 = new TaskList("list2");
        l2.id = 2L;
        parent.addTaskList(l2);
        Task t1 = new Task("task1", 1, "");
        t1.id = 1L;
        Task t2 = new Task("task2", 2, "");
        t2.id = 2L;
        Task t3 = new Task("task3", 3, "");
        t3.id = 3L;
        l1.addTask(t1);
        t1.setTaskList(l1);
        l1.addTask(t2);
        t2.setTaskList(l1);
        l1.addTask(t3);
        t3.setTaskList(l1);
        lists.addAll(List.of(l1, l2));
        tasks.addAll(List.of(t1, t2, t3));
        taskRepo.tasks.addAll(List.of(t1, t2, t3));
        listRepo.lists.addAll(List.of(l1, l2));
    }

    @Test
    public void testGetAll() {
        assertEquals(taskController.getAll(), tasks);
        assertTrue(taskRepo.calledMethods.contains("findAll") &&
                taskRepo.calledMethods.size() == 1);
    }

    @Test
    public void testValidGetById() {
        assertEquals(taskController.getById(1L), ResponseEntity.ok(tasks.get(0)));
        assertTrue(taskRepo.calledMethods.contains("findById"));
    }

    @Test
    public void testNegativeIdGetById() {
        assertEquals(taskController.getById(-1L), ResponseEntity.badRequest().body("Invalid ID."));
    }

    @Test
    public void testInexistentIdGetById() {
        assertEquals(taskController.getById(5L), ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(taskRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testInexistentListMoveTask() {
        assertEquals(taskController.moveTask(4L, 1, 2L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testMoveTask() {
        assertEquals(taskController.moveTask(2L, 1, 2L),
                ResponseEntity.ok("Changed successfully!"));
        assertEquals(tasks.get(1).getTaskList(), lists.get(1));
        assertEquals(tasks.get(1).index, 1);
        assertEquals(tasks.get(0).index, 1);
        assertEquals(tasks.get(2).index, 2);
    }

    @Test
    public void testInexistentListCreateTask() {
        assertEquals(taskController.createTask(5L, null),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testNullTaskCreateTask() {
        assertEquals(taskController.createTask(1L, null),
                ResponseEntity.badRequest().body("Invalid data."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task("newTask", 1, "");
        assertEquals(taskController.createTask(1L, newTask),
                ResponseEntity.ok(taskRepo.saveAndFlush(newTask)));
        assertEquals(newTask.index, 4);
        assertEquals(newTask.getTaskList(), lists.get(0));
    }

    @Test
    public void testNullTaskUpdateTask() {
        assertEquals(taskController.updateTask(null),
                ResponseEntity.badRequest().body("Invalid data."));
    }

    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task("Task1Updated", 1, "this is updated");
        updatedTask.id = 1L;
        assertEquals(taskController.updateTask(updatedTask), ResponseEntity.ok("Task updated."));
        assertEquals(tasks.get(0).id, 1L);
        assertEquals(tasks.get(0).name, "Task1Updated");
        assertEquals(tasks.get(0).description, "this is updated");
    }

    @Test
    public void testInexistentTaskDeleteById() {
        assertEquals(taskController.deleteById(10L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(taskRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testDeleteById() {
        assertEquals(taskController.deleteById(1L),
                ResponseEntity.ok("Successfully deleted."));
        assertTrue(taskRepo.calledMethods.contains("existsById"));
        assertTrue(taskRepo.calledMethods.contains("deleteById"));
        assertFalse(taskRepo.tasks.contains(tasks.get(0)));
    }


}
