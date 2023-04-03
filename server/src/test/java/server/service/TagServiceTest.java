package server.service;

import commons.Board;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.BoardRepository;
import server.database.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceTest {
    private TagRepository tagRepo;
    private BoardRepository boardRepo;
    private TagService tagService;
    private Tag t1;
    private Tag t2;
    private Tag t3;
    private Tag t4;
    private List<Tag> tags;
    private Long inc;

    @BeforeEach
    public void setUp() {
        t1 = new Tag("t1", "");
        t2 = new Tag("t2", "");
        t3 = new Tag("t3", "");
        t4 = new Tag("t4", "");
        tagRepo = mock(TagRepository.class);
        boardRepo = mock(BoardRepository.class);
        tagService = new TagService(tagRepo, boardRepo);
        tags = new ArrayList<>();
        inc = 0L;

        when(tagRepo.findAll()).thenReturn(tags);

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return tags.stream().anyMatch(t -> t.id == id);
        }).when(tagRepo).existsById(Mockito.any(Long.class));

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return tags.stream().filter(t -> t.id == id).findFirst();
        }).when(tagRepo).findById(Mockito.any(Long.class));

        doAnswer(invocation -> {
            Tag t = invocation.getArgument(0);
            t.id = inc++;
            tags.add(t);
            return t;
        }).when(tagRepo).saveAndFlush(Mockito.any(Tag.class));

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            Tag delete = tags.stream().filter(t -> t.id == id).findFirst().get();
            tags.remove(delete);
            return null;
        }).when(tagRepo).deleteById(Mockito.any(Long.class));
    }

    @Test
    public void testGetAll() {
        tags.addAll(List.of(t1, t2, t3, t4));
        assertEquals(tagService.getAll(), List.of(t1, t2, t3, t4));
        verify(tagRepo, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        t1.id = 0L;
        t2.id = 1L;
        tags.addAll(List.of(t1, t2));
        assertEquals(tagService.getById(1L), ResponseEntity.ok(t2));
        verify(tagRepo, times(1)).existsById(1L);
        verify(tagRepo, times(1)).findById(1L);
    }

    @Test
    public void testGetByIdInvalid() {
        t1.id = 0L;
        t2.id = 1L;
        tags.addAll(List.of(t1, t2));
        assertEquals(tagService.getById(2L), ResponseEntity.badRequest().build());
        verify(tagRepo, times(1)).existsById(2L);
    }

    @Test
    public void testUpdate() {
        t1.id = 0L;
        t2.id = 1L;
        Board board = new Board("name", "");
        board.id = 0L;
        Tag updated = new Tag("updated", "");
        updated.id = 0L;
        t1.board = board;
        tags.addAll(List.of(t1, t2));
        assertEquals(tagService.update(updated), ResponseEntity.ok(t1));
        verify(tagRepo, times(1)).existsById(0L);
        verify(tagRepo, times(1)).findById(0L);
    }

    @Test
    public void testUpdateInvalid() {
        Tag updated = new Tag("updated", "");
        updated.id = 0L;
        assertEquals(tagService.update(updated), ResponseEntity.badRequest().build());
        verify(tagRepo, times(1)).existsById(0L);
    }

    @Test
    public void testCreate() {
        Board b = new Board();
        when(boardRepo.existsById(0L)).thenReturn(true);
        when(boardRepo.findById(0L)).thenReturn(Optional.of(b));
        t1.board = b;
        assertEquals(tagService.create(t1, 0L), ResponseEntity.ok(t1));
        assertEquals(t1.id, 0L);
        verify(tagRepo, times(1)).saveAndFlush(t1);
        verify(boardRepo, times(1)).existsById(0L);
        verify(boardRepo, times(1)).findById(0L);
    }

    @Test
    public void testCreateInvalid() {
        Tag test = new Tag();
        assertEquals(tagService.create(test, 0L), ResponseEntity.badRequest().build());
    }

    @Test
    public void testDelete() {
        t1.id = 0L;
        t2.id = 1L;
        Board board = new Board("name", "");
        board.id = 0L;
        t1.board = board;
        when(boardRepo.findById(0L)).thenReturn(Optional.of(board));
        tags.addAll(List.of(t1, t2));
        assertEquals(tagService.delete(0L), ResponseEntity.ok().build());
        assertEquals(tags, List.of(t2));
        verify(tagRepo, times(1)).existsById(0L);
        verify(tagRepo, times(1)).deleteById(0L);
    }

    @Test
    public void testDeleteInvalid() {
        assertEquals(tagService.delete(0L), ResponseEntity.badRequest().build());
        verify(tagRepo, times(1)).existsById(0L);
    }

    @Test
    public void testGetUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Board>>(5000L, noContent);
        assertEquals(res.getResult(), tagService.getUpdates().getResult());
    }

}
