package server.service;

import commons.Board;
import commons.Tag;
import commons.Task;
import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import server.api.TaskController;
import server.database.TagRepository;
import server.database.TaskListRepository;
import server.database.TaskRepository;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final TaskListRepository listRepo;
    private final TagRepository tagRepo;
    private Map<Object, Consumer<Board>> listenCreate;

    /**
     * Instantiate a new {@link TaskController}.
     *
     * @param taskRepo the {@link TaskRepository} to use.
     * @param listRepo the {@link TaskListRepository} to use.
     * @param tagRepo the {@link TagRepository} to use.
     */
    @Autowired
    public TaskService(TaskRepository taskRepo,
                          TaskListRepository listRepo,
                          TagRepository tagRepo) {
        this.taskRepo = taskRepo;
        this.listRepo = listRepo;
        this.tagRepo = tagRepo;
        this.listenCreate = new HashMap<>();
    }

    /**
     * Gets all Task objects.
     *
     * @return a list of all Task objects.
     */
    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    /**
     * Gets the Task object with the provided id.
     *
     * @param taskId is the id to be searched.
     * @return a ResponseEntity containing the Task, if it exists.
     */
    public ResponseEntity<?> getById(long taskId) {

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
    public ResponseEntity<String> moveTask(long newListId,int index, long taskId) {
        if (index < 0 || newListId < 0 || !listRepo.existsById((newListId)) ||
                taskId < 0 || !taskRepo.existsById(taskId))
            return ResponseEntity.badRequest().body("Invalid ID.");

        Task t = taskRepo.findById(taskId).get();
        TaskList newList = listRepo.findById(newListId).get();

        changeIndexesOldList(t.getTaskList(), t.index);
        changeIndexesNewList(newList, index);
        t.setTaskList(newList);

        t.index = index;
        Task updated = taskRepo.saveAndFlush(t);

        Board board = updated.getTaskList().getBoard();
        board.toString();
        listenCreate.forEach((k, l) -> l.accept(board));

        return ResponseEntity.ok("Changed successfully!");
    }

    /**
     * Creates a new Task and adds it to the TaskList having the provided id.
     *
     * @param listId is the id of the TaskList.
     * @param task   is the task to be added.
     * @return a ResponseEntity containing the new Task.
     */
    public ResponseEntity<?> createTask(long listId, Task task) {
        if (listId < 0 || !listRepo.existsById(listId))
            return ResponseEntity.badRequest().body("Invalid ID.");
        if (task == null || task.name == null)
            return ResponseEntity.badRequest().body("Invalid data.");
        Optional<Integer> i = listRepo.findById(listId).get().tasks.stream().
                map(t -> t.index).max(Integer::compare);

        int index = 0;
        if (i.isPresent()) index = i.get() + 1;
        task.index = index;
        task.setTaskList(listRepo.findById(listId).get());
        Task saved = taskRepo.saveAndFlush(task);

        Board board = saved.getTaskList().getBoard();
        board.toString();
        listenCreate.forEach((k, l) -> l.accept(board));

        return ResponseEntity.ok(saved);
    }

    /**
     * Updates the task name and description of a task.
     *
     * @param task contains the updates.
     * @return a ResponseEntity with the status of the operation.
     */
    public ResponseEntity<String> updateTask(Task task) {
        if (task == null || task.id < 0 || !taskRepo.existsById(task.id))
            return ResponseEntity.badRequest().body("Invalid data.");

        Task current = taskRepo.findById(task.id).get();
        current.name = task.name;
        current.description = task.description;
        current.subtasks = task.subtasks;
        current.color = task.color;

        Set<Tag> copy = Set.copyOf(current.tags);
        for(Tag tag : copy) {
            Tag onServer = tagRepo.findById(tag.id).get();
            current.tags.remove(onServer);
            onServer.removeFrom(current);
            tagRepo.saveAndFlush(onServer);
        }
        copy = Set.copyOf(task.tags);
        for(Tag tag : copy) {
            Tag onServer = tagRepo.findById(tag.id).get();
            current.tags.add(onServer);
            onServer.applyTo(current);
            tagRepo.saveAndFlush(onServer);
        }
        Task updated = taskRepo.saveAndFlush(current);

        Board board = updated.getTaskList().getBoard();
        board.toString();
        listenCreate.forEach((k, l) -> l.accept(board));

        return ResponseEntity.ok("Task updated.");
    }

    /**
     * Deletes the Task with the given id.
     *
     * @param taskId is the id.
     * @return a ResponseEntity containing the status of the operation.
     */
    @Transactional
    public ResponseEntity<String> deleteById(long taskId) {
        if (taskId < 0 || !taskRepo.existsById(taskId))
            return ResponseEntity.badRequest().body("Invalid ID.");

        Task deleted = taskRepo.findById(taskId).get();
        TaskList old = listRepo.findById(deleted.getTaskList().id).get();
        old.removeTask(deleted);
        listRepo.save(old);

        int index = deleted.index;

        for(Tag tag : deleted.tags) {
            tag.removeFrom(deleted);
            tagRepo.saveAndFlush(tag);
        }

        taskRepo.delete(deleted);
        taskRepo.flush();
        changeIndexesOldList(old, index);
        Board board = listRepo.findById(old.id).get().getBoard();
        board.toString();
        listenCreate.forEach((k, l) -> l.accept(board));

        return ResponseEntity.ok("Successfully deleted.");
    }

    /**
     * Handles long polling updates.
     *
     * @return a Response containing the modified Board.
     */
    public DeferredResult<ResponseEntity<Board>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Board>>(5000L, noContent);

        var key = new Object();
        listenCreate.put(key, b -> {
            res.setResult(ResponseEntity.ok((Board) b));
        });
        res.onCompletion(() -> {
            listenCreate.remove(key);
        });

        return res;
    }

    /**
     * Increments indexes larger than index so a new Task can be inserted in the list.
     *
     * @param list  is the TaskList in which it updates the Task indexes.
     * @param index is the index of the inserted Task.
     */
    public void changeIndexesNewList(TaskList list, int index) {
        list.tasks.stream().filter(t -> t.index >= index).forEach(t -> t.index++);
        listRepo.saveAndFlush(list);
    }

    /**
     * Decrements indexes larger that index to keep the order after removing a Task.
     *
     * @param list  is the TaskList in which it decrements the Task indexes.
     * @param index is the index of the removed Task.
     */
    public void changeIndexesOldList(TaskList list, int index) {
        list.tasks.stream().filter(t -> t.index > index).forEach(t -> t.index--);
        listRepo.saveAndFlush(list);
    }
}
