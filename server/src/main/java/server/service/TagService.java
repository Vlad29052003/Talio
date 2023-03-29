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

    @Autowired
    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    public ResponseEntity<?> getById(long id) {
        if(tagRepo.existsById(id)) {
            return ResponseEntity.ok(tagRepo.findById(id).get());
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> update(Tag tag) {
        if(tagRepo.existsById(tag.id)) {
            Tag updated = tagRepo.findById(tag.id).get();
            updated.name = tag.name;
            updated.color = tag.color;
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> create(Tag tag) {
        if(tag.board == null)return ResponseEntity.badRequest().build();
        Tag saved = tagRepo.saveAndFlush(tag);
        return ResponseEntity.ok(saved);
    }

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
