package server.api;

import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.TagService;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    /**
     * Creates a new {@link TagController} object.
     *
     * @param tagService is the TagService to be used.
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Gets all Tags from the database.
     *
     * @return a list of Tags.
     */
    @GetMapping(path = {"", "/"})
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    /**
     * Gets a Tag by its id.
     *
     * @param id is the id to be used.
     * @return a response, potentially containing the searched Tag.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return tagService.getById(id);
    }

    /**
     * Updates a Tag.
     *
     * @param tag is the tag with updated data.
     * @return a response, potentially containing the updated Tag, if valid.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<?> update(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    /**
     * Creates a Tag.
     *
     * @param boardId is the id of the Board.
     * @param tag     is the new Tag.
     * @return a response, potentially containing the new Tag, if valid.
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<?> create(@PathVariable("boardId") long boardId,
                                    @RequestBody Tag tag) {
        return tagService.create(tag, boardId);
    }

    /**
     * Deletes a Tag.
     *
     * @param id is the id of the Tag to be deleted.
     * @return a response containing the status of the action.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return tagService.delete(id);
    }

}
