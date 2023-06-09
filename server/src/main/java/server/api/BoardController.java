package server.api;

import commons.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.BoardRepository;
import server.mutations.BoardChangeQueue;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository repo;
    public final BoardChangeQueue changes;

    /**
     * Instantiate a new {@link BoardController}.
     * @param repo the {@link BoardRepository} to use.
     * @param changes the {@link BoardChangeQueue} to use
     */
    @Autowired
    public BoardController(BoardRepository repo, BoardChangeQueue changes) {
        this.repo = repo;
        this.changes = changes;
    }

    /**
     * Retrieves all boards from the repository
     * @return All boards
     */
    @GetMapping(path = { "", "/" })
    public List<Board> getAll() {
        return repo.findAll();
    }

    /**
     * Adds a board to the repository
     * @param board to be added
     * @return The created board
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {

        if (board == null || board.name == null || board.lists == null){
            return ResponseEntity.badRequest().build();
        }

        Board saved = repo.save(board);
        changes.addCreated(board.id, saved);
        return ResponseEntity.ok(saved);
    }

    /**
     * Finds and returns the board with the corresponding id
     * @param id of the board
     * @return ResponseEntity with OK response with the board as body
     *         or with a BadRequest or NotFound
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable("id") long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Board> board = repo.findById(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates a board in the repository with the values of the passed on board
     * @param id of the board
     * @param board entity with new values
     * @return ResponseEntity with either BadRequest, NotFound or OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") long id,
                                             @RequestBody Board board) {
        if (id < 0 || board == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Board> optLocalBoard = repo.findById(id);
        if(optLocalBoard.isEmpty()) return ResponseEntity.notFound().build();

        Board localBoard = optLocalBoard.get();
        localBoard.name = board.name;
        localBoard.password = board.password;
        localBoard.backgroundColor = board.backgroundColor;
        localBoard.fontColor = board.fontColor;
        localBoard.listBackgroundColor = board.listBackgroundColor;
        localBoard.listFontColor = board.listFontColor;

        Board saved = repo.save(localBoard);
        changes.addChanged(id, saved);

        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a board and its dependents from the repository
     * @param id of the board
     * @return ResponseEntity with either BadRequest, NotFound or OK
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Board> deleteBoard(@PathVariable("id") long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        if(!repo.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        changes.addDeleted(id);
        return ResponseEntity.ok().build();
    }

}