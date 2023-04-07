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
import server.database.BoardRepository;
import server.database.TaskListRepository;
import server.mutations.BoardChangeQueue;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/task_lists")
public class TaskListController {
    private final TaskListRepository repo;
    private final BoardRepository boardRepo;
    private final BoardChangeQueue changes;

    /**
     * Instantiate a new {@link TaskListController}.
     *
     * @param boardRepo the {@link BoardRepository} to use.
     * @param repo the {@link TaskListRepository} to use.
     * @param changes the {@link BoardChangeQueue} to use.
     */
    @Autowired
    public TaskListController(BoardRepository boardRepo,
                              TaskListRepository repo,
                              BoardChangeQueue changes) {
        this.boardRepo = boardRepo;
        this.repo = repo;
        this.changes = changes;
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

        Board parent = saved.getBoard();
        parent.toString();
        changes.addChanged(parent.id, parent);

        return ResponseEntity.ok(saved);
    }

    /**
     * Creates a new TaskList.
     *
     * @param boardId is the id of the board.
     * @param list The TaskList object to add.
     * @return a ResponseEntity containing the status of the operation.
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<?> add(@PathVariable("boardId") long boardId,
                                 @RequestBody TaskList list) {
        if (isNullOrEmpty(list.name)
                || list.tasks == null
                || !boardRepo.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardRepo.findById(boardId).get();
        board.addTaskList(list);
        repo.save(list);

        Board parent = board;
        parent.toString();
        changes.addChanged(parent.id, parent);

        return ResponseEntity.ok(boardRepo.findById(boardId).get());
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
        if (id < 0 || !repo.existsById(id))
            return ResponseEntity.badRequest().body("Invalid ID.");

        TaskList taskList = repo.findById(id).get();
        Board parent = boardRepo.findById(taskList.getBoard().id).get();
        parent.removeTaskList(taskList);

        boardRepo.save(parent);
        repo.delete(taskList);

        parent.toString();
        changes.addChanged(parent.id, parent);

        return ResponseEntity.ok().build();
    }

    private static boolean isNullOrEmpty(String s) { return s == null || s.isEmpty(); }
}
