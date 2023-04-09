package server.api;

import commons.Board;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.TaskService;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    /**
     * Instantiate a new {@link TaskController}.
     *
     * @param taskService the {@link TaskService} to use.
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Gets all Task objects.
     *
     * @return a list of all Task objects.
     */
    @GetMapping(path = {"", "/"})
    public List<Task> getAll() {
        return taskService.getAll();
    }

    /**
     * Gets the Task object with the provided id.
     *
     * @param taskId is the id to be searched.
     * @return a ResponseEntity containing the Task, if it exists.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getById(@PathVariable("taskId") long taskId) {
        return taskService.getById(taskId);
    }

    /**
     * Moves Task object with the given id to the TaskList object
     * with the specified id, and sets a new index for it.
     * Useful for Drag and Drop feature.
     *
     * @param newListId is the id of the TaskList object.
     * @param index     is the position within the TaskList.
     * @param taskId    is the id of the Task object.
     * @return the status of the operation.
     */
    @PostMapping("/{newListId}/{index}/{taskId}")
    public ResponseEntity<String> moveTask(@PathVariable("newListId") long newListId,
                                           @PathVariable("index") int index,
                                           @PathVariable("taskId") long taskId) {
        return taskService.moveTask(newListId, index, taskId);
    }

    /**
     * Creates a new Task and adds it to the TaskList having the provided id.
     *
     * @param listId is the id of the TaskList.
     * @param task   is the task to be added.
     * @return a ResponseEntity containing the new Task.
     */
    @PostMapping("/{listId}")
    public ResponseEntity<?> createTask(@PathVariable("listId") long listId,
                                        @RequestBody Task task) {
        return taskService.createTask(listId, task);
    }

    /**
     * Updates the task name and description of a task.
     *
     * @param task contains the updates.
     * @return a ResponseEntity with the status of the operation.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    /**
     * Deletes the Task with the given id.
     *
     * @param taskId is the id.
     * @return a ResponseEntity containing the status of the operation.
     */
    @DeleteMapping("/{taskId}")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable("taskId") long taskId) {
        return taskService.deleteById(taskId);
    }

    /**
     * Handles long polling updates.
     *
     * @return a Response containing the modified Board.
     */
    @GetMapping("/getUpdates")
    public DeferredResult<ResponseEntity<Board>> getUpdates() {
        return taskService.getUpdates();
    }
}
