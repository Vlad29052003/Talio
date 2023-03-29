package server.api;

import commons.Board;
import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import server.database.TaskListRepository;
import server.database.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/task_lists")
public class TaskListController {
    private final TaskListRepository repo;
    private final TaskRepository taskRepo;

    /**
     * Instantiate a new {@link TaskListController}.
     * @param repo the {@link TaskListRepository} to use
     * @param taskRepo the {@link TaskRepository} to use
     */
    @Autowired
    public TaskListController(TaskListRepository repo, TaskRepository taskRepo) {
        this.repo = repo;
        this.taskRepo = taskRepo;
    }

    /**
     * Get all the TaskLists
     *
     * @return a List containing all the task lists.
     */
    @GetMapping(path = { "", "/" })
    public List<TaskList> getAll() { return repo.findAll(); }

    /**
     * Get a TaskList with a given id.
     *
     * @param id is the id of the list to get.
     * @return a ResponseEntity containing the TaskList if it exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskList> getById(@PathVariable("id") long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TaskList> list = repo.findById(id);
        return list.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates a task list in the repository with the values of the passed on task list.
     * Note that the id of the list argument has to be the id of
     * the actual list that needs to be modified.
     * This means that you need to pass in the actual modified list, not a new one with new values.
     *
     * @param list entity with new values.
     * @return a ResponseEntity containing the TaskList if it exists.
     */
    @PutMapping({"", "/"})
    public ResponseEntity<TaskList> update(@RequestBody TaskList list) {
        if (list == null
                || list.id < 0
                || isNullOrEmpty(list.name)
                || list.tasks == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<TaskList> optLocalTaskList = repo.findById(list.id);
        if (optLocalTaskList.isEmpty()) return ResponseEntity.notFound().build();

        TaskList localList = optLocalTaskList.get();
        localList.name = list.name;

        TaskList saved = repo.save(localList);

        return ResponseEntity.ok(saved);
    }

    /**
     * Creates a new TaskList.
     *
     * @param list The TaskList object to add.
     * @param board The Board that the new TaskList belongs to.
     * @return a ResponseEntity containing the status of the operation.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<TaskList> add(@RequestBody TaskList list, @RequestBody Board board) {
        if (isNullOrEmpty(list.name) || list.tasks == null) {
            return ResponseEntity.badRequest().build();
        }

        list.setBoard(board);

        TaskList saved = repo.save(list);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a TaskList given by an id.
     *
     * @param id is the id of the task list to delete.
     * @return a ResponseEntity containing the status of the operation.
     */
    @DeleteMapping("/{taskListId}")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable("taskListId") long id) {
        if (id < 0)
            return ResponseEntity.badRequest().body("Invalid ID.");

        Optional<TaskList> optLocal = repo.findById(id);
        return optLocal.map((opt) -> {
            opt.getBoard().removeTaskList(opt);
            taskRepo.deleteAll(opt.tasks);
            repo.deleteById(opt.id);
            return ResponseEntity.ok("Successfully deleted.");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private static boolean isNullOrEmpty(String s) { return s == null || s.isEmpty(); }
}
