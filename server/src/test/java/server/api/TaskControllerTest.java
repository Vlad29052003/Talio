package server.api;

import commons.Board;
import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.TaskService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskControllerTest {
    private TaskController taskController;
    private TaskService taskService;
    private List<TaskList> lists;
    private List<Task> tasks;
    private long inc = 4L;

    @BeforeEach
    public void setup() {
        lists = new ArrayList<>();
        tasks = new ArrayList<>();
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
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
        when(taskService.getAll()).thenReturn(tasks);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            var res = tasks.stream().filter(t -> t.id == id).findFirst();
            if(res.isPresent())return ResponseEntity.ok(res.get());
            return ResponseEntity.badRequest().body("Invalid ID.");
        }).when(taskService).getById(Mockito.any(Long.class));
        doAnswer(invocation -> {
            Long newListId = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            Long taskId = invocation.getArgument(2);
            var optList = lists.stream().filter(l -> l.id == newListId).findFirst();
            var optTask = tasks.stream().filter(t -> t.id == taskId).findFirst();
            if(optList.isEmpty() || optTask.isEmpty()) return ResponseEntity.badRequest().body("Invalid ID.");
            Task t = optTask.get();
            TaskList tl = optList.get();
            t.setTaskList(tl);
            tl.addTask(t);
            t.index = index;
            return ResponseEntity.ok("Changed successfully!");
        }).when(taskService).moveTask(Mockito.any(Long.class), Mockito.any(Integer.class), Mockito.any(Long.class));
        doAnswer(invocation -> {
            Task t = invocation.getArgument(1);
            t.id = inc++;
            return ResponseEntity.ok(t);
        }).when(taskService).createTask(Mockito.any(Long.class), Mockito.any(Task.class));
        doAnswer(invocation -> {
            Task updated = invocation.getArgument(0);
            var res = tasks.stream().filter(t -> t.id == updated.id).findFirst();
            if(res.isPresent()) {
                Task t = res.get();
                t.name = updated.name;
                return ResponseEntity.ok("Task updated.");
            }
            return ResponseEntity.badRequest().body("Invalid ID.");
        }).when(taskService).updateTask(Mockito.any(Task.class));
        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            var res = tasks.stream().filter(t -> t.id == id).findFirst();
            if(res.isPresent()) {
                tasks.remove(res.get());
                return ResponseEntity.ok("Successfully deleted.");
            }
            return ResponseEntity.badRequest().body("Invalid ID.");
        }).when(taskService).deleteById(Mockito.any(Long.class));
    }

    @Test
    public void testGetAll() {
        assertEquals(taskController.getAll(), tasks);
        verify(taskService, times(1)).getAll();
    }

    @Test
    public void testGetById() {
        assertEquals(taskController.getById(1L), ResponseEntity.ok(tasks.get(0)));
        verify(taskService, times(1)).getById(1L);
    }

    @Test
    public void testInexistentGetById() {
        assertEquals(taskController.getById(0L), ResponseEntity.badRequest().body("Invalid ID."));
        verify(taskService, times(1)).getById(0L);
    }

    @Test
    public void testMoveTask() {
        assertEquals(taskController.moveTask(2L, 1, 2L), ResponseEntity.ok("Changed successfully!"));
        verify(taskService, times(1)).moveTask(2L, 1, 2L);
        assertEquals(tasks.get(1).index, 1);
        assertEquals(tasks.get(1).getTaskList(), lists.get(1));
    }

    @Test
    public void testInexistentMoveTask() {
        assertEquals(taskController.moveTask(2L, 1, 5L), ResponseEntity.badRequest().body("Invalid ID."));
        verify(taskService, times(1)).moveTask(2L, 1, 5L);
    }

    @Test public void testCreate() {
        Task newest = new Task("new", 0, "");
        Task expected = new Task("new", 0, "");
        expected.id = 4L;
        assertEquals(taskController.createTask(1L, newest), ResponseEntity.ok(expected));
    }

    @Test
    public void testUpdateTask() {
        Task updated = new Task("Updated", 1, "");
        updated.id = 1L;
        assertEquals(taskController.updateTask(updated), ResponseEntity.ok("Task updated."));
        assertEquals(tasks.get(0).name, "Updated");
        verify(taskService, times(1)).updateTask(updated);
    }

    @Test
    public void testInexistentUpdateTask() {
        Task updated = new Task("Updated", 1, "");
        updated.id = 5L;
        assertEquals(taskController.updateTask(updated), ResponseEntity.badRequest().body("Invalid ID."));
        verify(taskService, times(1)).updateTask(updated);
    }

    @Test
    public void testDelete() {
        Task deleted = tasks.get(0);
        assertEquals(taskController.deleteById(1L), ResponseEntity.ok("Successfully deleted."));
        assertFalse(tasks.contains(deleted));
        verify(taskService, times(1)).deleteById(1L);
    }

    @Test
    public void testInexistentDelete() {
        assertEquals(taskController.deleteById(5L), ResponseEntity.badRequest().body("Invalid ID."));
        verify(taskService, times(1)).deleteById(5L);
    }

    @Test
    public void testGetUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Board>>(5000L, noContent);
        when(taskService.getUpdates()).thenReturn(res);
        assertEquals(taskController.getUpdates().getResult(), res.getResult());
    }
}
