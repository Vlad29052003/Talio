package server.api;

import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
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
        TaskList l1 = new TaskList("list1");
        l1.id = 1L;
        TaskList l2 = new TaskList("list2");
        l2.id = 2L;
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
        assertEquals(taskRepo.calledMethods, List.of("existsById", "findById"));
    }

    @Test
    public void testNegativeIdGetById() {
        assertEquals(taskController.getById(-1L), ResponseEntity.badRequest().body("Invalid ID."));
    }

    @Test
    public void testInexistentIdGetById() {
        assertEquals(taskController.getById(5L), ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(taskRepo.calledMethods, List.of("existsById"));
    }

    @Test
    public void testInexistentListMoveTask() {
        assertEquals(taskController.moveTask(4L, 1L, 2L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(listRepo.calledMethods, List.of("existsById"));
    }

    @Test
    public void testMoveTask() {
        assertEquals(taskController.moveTask(2L, 1L, 2L),
                ResponseEntity.ok("Changed successfully!"));
        assertEquals(tasks.get(1).getTaskList(), lists.get(1));
        assertEquals(tasks.get(1).index, 1L);
        assertEquals(tasks.get(0).index, 1L);
        assertEquals(tasks.get(2).index, 2L);
        assertEquals(listRepo.calledMethods,
                List.of("existsById", "findById"));
        assertEquals(taskRepo.calledMethods, List.of("existsById", "findById", "saveAndFlush"));
    }

    @Test
    public void testInexistentListCreateTask() {
        assertEquals(taskController.createTask(5L, null),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(listRepo.calledMethods, List.of("existsById"));
    }

    @Test
    public void testNullTaskCreateTask() {
        assertEquals(taskController.createTask(1L, null),
                ResponseEntity.badRequest().body("Invalid data."));
        assertEquals(listRepo.calledMethods, List.of("existsById"));
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task("newTask", 1L, "");
        assertEquals(taskController.createTask(1L, newTask),
                ResponseEntity.ok(taskRepo.saveAndFlush(newTask)));
        assertEquals(newTask.index, 4L);
        assertEquals(newTask.getTaskList(), lists.get(0));
        assertEquals(listRepo.calledMethods, List.of("existsById", "findById", "findById"));
        assertEquals(taskRepo.calledMethods, List.of("saveAndFlush", "saveAndFlush"));
    }

    @Test
    public void testNullTaskUpdateTask() {
        assertEquals(taskController.updateTask(null),
                ResponseEntity.badRequest().body("Invalid data."));
    }

    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task("Task1Updated", 1L, "this is updated");
        updatedTask.id = 1L;
        assertEquals(taskController.updateTask(updatedTask), ResponseEntity.ok("Task updated."));
        assertEquals(tasks.get(0).id, 1L);
        assertEquals(tasks.get(0).name, "Task1Updated");
        assertEquals(tasks.get(0).description, "this is updated");
        assertEquals(taskRepo.calledMethods, List.of("existsById", "findById", "saveAndFlush"));
    }

    @Test
    public void testInexistentTaskDeleteById() {
        assertEquals(taskController.deleteById(10L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(taskRepo.calledMethods, List.of("existsById"));
    }

    @Test
    public void testDeleteById() {
        assertEquals(taskController.deleteById(1L),
                ResponseEntity.ok("Successfully deleted."));
        assertEquals(taskRepo.calledMethods, List.of("existsById", "findById", "delete", "flush"));
        assertFalse(taskRepo.tasks.contains(tasks.get(0)));
    }


}
