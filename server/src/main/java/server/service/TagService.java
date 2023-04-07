package server.service;

import commons.Board;
import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.BoardRepository;
import server.database.TagRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class TagService {
    private final TagRepository tagRepo;
    private final BoardRepository boardRepo;
    private Map<Object, Consumer<Board>> listen;

    /**
     * Creates a new {@link TagService} object.
     *
     * @param tagRepo   is the TagRepository to be used.
     * @param boardRepo is the BoardRepository to be used.
     */
    @Autowired
    public TagService(TagRepository tagRepo, BoardRepository boardRepo) {
        this.tagRepo = tagRepo;
        this.boardRepo = boardRepo;
        this.listen = new HashMap<>();
    }

    /**
     * Gets all Tags from the database.
     *
     * @return a list of Tags.
     */
    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    /**
     * Gets a Tag by its id.
     *
     * @param id is the id to be used.
     * @return a response, potentially containing the searched Tag.
     */
    public ResponseEntity<?> getById(long id) {
        if (tagRepo.existsById(id)) {
            return ResponseEntity.ok(tagRepo.findById(id).get());
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates a Tag.
     *
     * @param tag is the tag with updated data.
     * @return a response, potentially containing the updated Tag, if valid.
     */
    public ResponseEntity<?> update(Tag tag) {
        if (tagRepo.existsById(tag.id)) {
            Tag updated = tagRepo.findById(tag.id).get();
            updated.name = tag.name;
            updated.color = tag.color;
            updated = tagRepo.saveAndFlush(updated);

            Board board = updated.board;
            board.toString();
            listen.forEach((k, l) -> l.accept(board));

            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Creates a Tag.
     *
     * @param tag     is the new Tag.
     * @param boardId is the id of the Board.
     * @return a response, potentially containing the new Tag, if valid.
     */
    public ResponseEntity<?> create(Tag tag, long boardId) {
        if (!boardRepo.existsById(boardId) || tag == null)
            return ResponseEntity.badRequest().build();
        Board board = boardRepo.findById(boardId).get();
        Tag saved = tagRepo.saveAndFlush(tag);
        board.tags.add(saved);
        tag.board = board;
        boardRepo.saveAndFlush(board);

        board.toString();
        listen.forEach((k, l) -> l.accept(board));

        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a Tag.
     *
     * @param id is the id of the Tag to be deleted.
     * @return a response containing the status of the action.
     */
    public ResponseEntity<?> delete(long id) {
        if (tagRepo.existsById(id)) {
            long boardId = tagRepo.findById(id).get().board.id;
            tagRepo.deleteById(id);
            tagRepo.flush();
            Board board = boardRepo.findById(boardId).get();
            board.toString();
            listen.forEach((k, l) -> l.accept(board));

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Handles long polling.
     *
     * @return the response containing an updated board,
     * ot no content if no updates are made.
     */
    public DeferredResult<ResponseEntity<Board>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Board>>(5000L, noContent);

        var key = new Object();
        listen.put(key, b -> {
            res.setResult(ResponseEntity.ok((Board) b));
        });
        res.onCompletion(() -> {
            listen.remove(key);
        });

        return res;
    }
}
