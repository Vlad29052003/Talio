package server.api;

import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import server.service.TagService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagControllerTest {
    private TagController tagController;
    private TagService tagService;
    private List<Tag> tags;
    private Tag t1;
    private Tag t2;
    private Tag t3;
    private Tag t4;
    private long inc = 0L;

    @BeforeEach
    public void setUp() {
        tagService = mock(TagService.class);
        tagController = new TagController(tagService);
        t1 = new Tag("tag1", "");
        t2 = new Tag("tag2", "");
        t3 = new Tag("tag3", "");
        t4 = new Tag("tag4", "");
        tags = new ArrayList<>();

        when(tagService.getAll()).thenReturn(tags);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            var tag = tags.stream().filter(t -> t.id == id).findFirst();
            if (tag.isPresent()) return ResponseEntity.ok(tag.get());
            return ResponseEntity.badRequest().build();
        }).when(tagService).getById(Mockito.any(Long.class));

        doAnswer(invocation -> {
            Tag req = invocation.getArgument(0);
            var t = tags.stream().filter(tag -> tag.id == req.id).findFirst();
            if (t.isPresent()) {
                Tag updated = t.get();
                updated.name = req.name;
                updated.color = req.color;
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.badRequest().build();
        }).when(tagService).update(Mockito.any(Tag.class));

        doAnswer(invocation -> {
            Tag req = invocation.getArgument(0);
            req.id = inc++;
            tags.add(req);
            return ResponseEntity.ok(req);
        }).when(tagService).create(Mockito.any(Tag.class), Mockito.any(Long.class));

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            var t = tags.stream().filter(tag -> tag.id == id).findFirst();
            if (t.isPresent()) {
                Tag tag = t.get();
                tags.remove(tag);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }).when(tagService).delete(Mockito.any(Long.class));
    }

    @Test
    public void testGetAll() {
        tags.addAll(List.of(t1, t2, t3, t4));
        assertEquals(tagController.getAll(), List.of(t1, t2, t3, t4));
        verify(tagService, times(1)).getAll();
    }

    @Test
    public void getById() {
        tagController.create(1L, t1);
        assertEquals(tagController.getById(0L), ResponseEntity.ok(t1));
        verify(tagService, times(1)).getById(0L);
    }

    @Test
    public void testCreate() {
        assertEquals(tagController.create(0L, t1), ResponseEntity.ok(t1));
        assertEquals(t1.id, 0L);
        verify(tagService, times(1)).create(t1, 0L);
    }

    @Test
    public void testUpdate() {
        tagController.create(0L, t1);
        tagController.create(0L, t2);
        tagController.create(0L, t3);
        tagController.create(0L, t4);

        Tag updated = new Tag("updated", "red");
        updated.id = 1L;

        assertEquals(tagController.update(updated), ResponseEntity.ok(t2));
        assertEquals(updated, t2);
        verify(tagService, times(1)).update(t2);
    }

    @Test
    public void testDelete() {
        tagController.create(0L, t1);
        assertEquals(tagController.delete(t1.id), ResponseEntity.ok().build());
        assertEquals(tags.size(), 0);
        verify(tagService, times(1)).delete(0L);
    }

}
