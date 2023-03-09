package server.api;

import commons.TaskList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.TaskListRepository;

import java.util.List;

@RestController
@RequestMapping("api/task_lists")
public class TaskListController {
    private final TaskListRepository repo;

    public TaskListController(TaskListRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<TaskList> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<TaskList> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path = {"", "/"})
    public ResponseEntity<TaskList> add(@RequestBody TaskList list) {
        if (isNullOrEmpty(list.name)) {
            return ResponseEntity.badRequest().build();
        }

        TaskList saved = repo.save(list);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) { return s == null || s.isEmpty(); }
}
