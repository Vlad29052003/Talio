package server.service;

import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.database.TagRepository;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepo;

    /**
     * Creates a new {@link TagService} object.
     *
     * @param tagRepo is the TagRepository to be used.
     */
    @Autowired
    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
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
        if(tagRepo.existsById(id)) {
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
        if(tagRepo.existsById(tag.id)) {
            Tag updated = tagRepo.findById(tag.id).get();
            updated.name = tag.name;
            updated.color = tag.color;
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Creates a Tag.
     *
     * @param tag is the new Tag.
     * @return a response, potentially containing the new Tag, if valid.
     */
    public ResponseEntity<?> create(Tag tag) {
        if(tag.board == null)return ResponseEntity.badRequest().build();
        Tag saved = tagRepo.saveAndFlush(tag);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a Tag.
     *
     * @param id is the id of the Tag to be deleted.
     * @return a response containing the status of the action.
     */
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if(tagRepo.existsById(id)) {
            tagRepo.deleteById(id);
            tagRepo.flush();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
