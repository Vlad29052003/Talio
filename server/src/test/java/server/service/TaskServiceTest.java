package server.service;

import commons.Board;
import commons.Tag;
import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.api.TaskListTestRepository;
import server.api.TestTaskRepository;
import server.database.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    private TaskListTestRepository listRepo;
    private TestTaskRepository taskRepo;
    private TaskService taskService;
    private TagRepository tagRepo;
    private List<TaskList> lists;

    private List<Task> tasks;

    @BeforeEach
    public void setup() {
        lists = new ArrayList<>();
        tasks = new ArrayList<>();
        listRepo = new TaskListTestRepository();
        taskRepo = new TestTaskRepository();
        tagRepo = mock(TagRepository.class);
        taskService = new TaskService(taskRepo, listRepo, tagRepo);
        Board b = new Board("test", "", "");
        TaskList l1 = new TaskList("list1");
        l1.id = 1L;
        b.addTaskList(l1);
        TaskList l2 = new TaskList("list2");
        l2.id = 2L;
        b.addTaskList(l2);
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
        b.addTaskList(l1);
        l1.setBoard(b);
        b.addTaskList(l2);
        l2.setBoard(b);
        lists.addAll(List.of(l1, l2));
        tasks.addAll(List.of(t1, t2, t3));
        taskRepo.getTasks().addAll(List.of(t1, t2, t3));
        listRepo.lists.addAll(List.of(l1, l2));
    }

    @Test
    public void testGetAll() {
        assertEquals(taskService.getAll(), tasks);
        assertTrue(taskRepo.getCalledMethods().contains("findAll") &&
                taskRepo.getCalledMethods().size() == 1);
    }

    @Test
    public void testValidGetById() {
        assertEquals(taskService.getById(1L), ResponseEntity.ok(tasks.get(0)));
        assertEquals(taskRepo.getCalledMethods(), List.of("existsById", "findById"));
    }

    @Test
    public void testNegativeIdGetById() {
        assertEquals(taskService.getById(-1L), ResponseEntity.badRequest().body("Invalid ID."));
    }

    @Test
    public void testInexistentIdGetById() {
        assertEquals(taskService.getById(5L), ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(taskRepo.getCalledMethods(), List.of("existsById"));
    }

    @Test
    public void testInexistentListMoveTask() {
        assertEquals(taskService.moveTask(4L, 1, 2L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testMoveTask() {
        assertEquals(taskService.moveTask(2L, 1, 2L),
                ResponseEntity.ok("Changed successfully!"));
        assertEquals(tasks.get(1).getTaskList(), lists.get(1));
        assertEquals(tasks.get(1).index, 1);
        assertEquals(tasks.get(0).index, 1);
        assertEquals(tasks.get(2).index, 2);
        Task moved = taskRepo.getTaskWithIt(2L);
        assertEquals(moved.index, 1);

        assertTrue(listRepo.calledMethods.contains("existsById")
                && listRepo.calledMethods.contains("findById"));
        assertEquals(taskRepo.getCalledMethods(),
                List.of("existsById", "findById", "saveAndFlush"));
    }

    @Test
    public void testInexistentListCreateTask() {
        assertEquals(taskService.createTask(5L, null),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testNullTaskCreateTask() {
        assertEquals(taskService.createTask(1L, null),
                ResponseEntity.badRequest().body("Invalid data."));
        assertTrue(listRepo.calledMethods.contains("existsById"));
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task("newTask", 1, "");
        assertEquals(taskService.createTask(1L, newTask),
                ResponseEntity.ok(taskRepo.saveAndFlush(newTask)));
        assertEquals(newTask.index, 4);
        assertEquals(newTask.getTaskList(), lists.get(0));
        assertTrue(taskRepo.getTasks().contains(newTask));
        assertEquals(listRepo.calledMethods, List.of("existsById", "findById", "findById"));
        assertEquals(taskRepo.getCalledMethods(), List.of("saveAndFlush", "saveAndFlush"));
    }

    @Test
    public void testNullTaskUpdateTask() {
        assertEquals(taskService.updateTask(null),
                ResponseEntity.badRequest().body("Invalid data."));
    }

    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task("Task1Updated", 1, "this is updated");
        Tag tag = new Tag();
        tag.id = 0L;
        when(tagRepo.findById(0L)).thenReturn(Optional.of(tag));
        updatedTask.tags.add(tag);
        tasks.get(0).tags.add(tag);
        updatedTask.id = 1L;

        assertEquals(taskService.updateTask(updatedTask), ResponseEntity.ok("Task updated."));
        assertEquals(tasks.get(0).name, "Task1Updated");
        assertEquals(tasks.get(0).description, "this is updated");
        assertEquals(taskRepo.getCalledMethods(),
                List.of("existsById", "findById", "saveAndFlush"));
    }

    @Test
    public void testInexistentTaskDeleteById() {
        assertEquals(taskService.deleteById(10L),
                ResponseEntity.badRequest().body("Invalid ID."));
        assertEquals(taskRepo.getCalledMethods(), List.of("existsById"));
    }

    @Test
    public void testDeleteById() {
        Task t1 = tasks.get(0);
        Tag tag = new Tag();
        tag.applyTo(t1);
        t1.tags.add(tag);
        taskRepo.getTasks().set(0, t1);
        assertEquals(taskService.deleteById(1L),
                ResponseEntity.ok("Successfully deleted."));
        assertEquals(taskRepo.getCalledMethods(),
                List.of("existsById", "findById", "delete", "deleteById", "flush"));
        assertFalse(taskRepo.getTasks().contains(tasks.get(0)));
    }

    @Test
    public void testGetUpdatesNoUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Board>>(5000L, noContent);
        assertEquals(taskService.getUpdates().getResult(), res.getResult());
    }
}
