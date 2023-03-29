package server.api;

import commons.Task;
import commons.TaskList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.TaskListRepository;
import server.database.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskRepository taskRepo;
    private final TaskListRepository listRepo;

    /**
     * Instantiate a new {@link TaskController}.
     * @param taskRepo the {@link TaskRepository} to use
     * @param listRepo the {@link TaskListRepository} to use
     */
    @Autowired
    public TaskController(TaskRepository taskRepo,
                          TaskListRepository listRepo) {
        this.taskRepo = taskRepo;
        this.listRepo = listRepo;
    }

    /**
     * Gets all Task objects.
     *
     * @return a list of all Task objects.
     */
    @GetMapping(path = {"", "/"})
    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    /**
     * Gets the Task object with the provided id.
     *
     * @param taskId is the id to be searched.
     * @return a ResponseEntity containing the Task, if it exists.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getById(@PathVariable("taskId") long taskId) {

        if (taskId < 0 || !taskRepo.existsById(taskId))
            return ResponseEntity.badRequest().body("Invalid ID.");

        return ResponseEntity.ok(taskRepo.findById(taskId).get());
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
    @Transactional
    public ResponseEntity<String> moveTask(@PathVariable("newListId") long newListId,
                                           @PathVariable("index") long index,
                                           @PathVariable("taskId") long taskId) {
        if (index < 0 || newListId < 0 || !listRepo.existsById((newListId)) ||
                taskId < 0 || !taskRepo.existsById(taskId))
            return ResponseEntity.badRequest().body("Invalid ID.");

        Task t = taskRepo.findById(taskId).get();
        TaskList newList = listRepo.findById(newListId).get();

        changeIndexesOldList(t.getTaskList(), t.index);
        changeIndexesNewList(newList, index);
        t.setTaskList(newList);

        t.index = index;
        taskRepo.saveAndFlush(t);

        return ResponseEntity.ok("Changed successfully!");
    }

    /**
     * Creates a new Task and adds it to the TaskList having the provided id.
     *
     * @param listId is the id of the TaskList.
     * @param task   is the task to be added.
     * @return a ResponseEntity containing the new Task.
     */
    @PostMapping("/{listId}")
    @Transactional
    public ResponseEntity<?> createTask(@PathVariable("listId") long listId,
                                        @RequestBody Task task) {
        if (listId < 0 || !listRepo.existsById(listId))
            return ResponseEntity.badRequest().body("Invalid ID.");
        if (task == null || task.name == null)
            return ResponseEntity.badRequest().body("Invalid data.");
        Optional<Long> i = listRepo.findById(listId).get().tasks.stream().
                map(t -> t.index).max(Long::compare);

        long index = 0;
        if (i.isPresent()) index = i.get() + 1;
        task.index = index;
        task.setTaskList(listRepo.findById(listId).get());
        Task saved = taskRepo.saveAndFlush(task);

        return ResponseEntity.ok(saved);
    }

    /**
     * Updates the task name and description of a task.
     *
     * @param task contains the updates.
     * @return a ResponseEntity with the status of the operation.
     */
    @PostMapping(path = {"", "/"})
    @Transactional
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        if (task == null || task.id < 0 || !taskRepo.existsById(task.id))
            return ResponseEntity.badRequest().body("Invalid data.");

        Task current = taskRepo.findById(task.id).get();
        current.name = task.name;
        current.description = task.description;
        taskRepo.saveAndFlush(current);

        return ResponseEntity.ok("Task updated.");
    }

    /**
     * Deletes the Task with the given id.
     *
     * @param taskId is the id.
     * @return a ResponseEntity containing the status of the operation.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteById(@PathVariable("taskId") long taskId) {
        if (taskId < 0 || !taskRepo.existsById(taskId))
            return ResponseEntity.badRequest().body("Invalid ID.");

        Task deleted = taskRepo.findById(taskId).get();
        TaskList old = deleted.getTaskList();
        long index = deleted.index;
        taskRepo.delete(deleted);
        taskRepo.flush();
        changeIndexesOldList(old, index);

        return ResponseEntity.ok("Successfully deleted.");
    }

    /**
     * Indexes indexes larger than index so a new Task can be inserted in the list.
     *
     * @param list  is the TaskList in which it updates the Task indexes.
     * @param index is the index of the inserted Task.
     */
    public void changeIndexesNewList(TaskList list, long index) {
        list.tasks.stream().filter(t -> t.index >= index).forEach(t -> t.index++);
        listRepo.saveAndFlush(list);
    }

    /**
     * Decrements indexes larger that index to keep the order after removing a Task.
     *
     * @param list  is the TaskList in which it decrements the Task indexes.
     * @param index is the index of the removed Task.
     */
    public void changeIndexesOldList(TaskList list, long index) {
        list.tasks.stream().filter(t -> t.index > index).forEach(t -> t.index--);
        listRepo.saveAndFlush(list);
    }

}
