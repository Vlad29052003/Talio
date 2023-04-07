package server.api;

import commons.Board;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import server.mutations.BoardChangeQueue;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class TaskListControllerTest {
    private TaskListTestRepository repo;
    private TestBoardRepository boardRepo;
    private TaskListController controller;
    private BoardChangeQueue changes;

    @BeforeEach
    public void setup() {
        repo = new TaskListTestRepository();
        boardRepo = new TestBoardRepository();
        changes = new BoardChangeQueue();
        controller = new TaskListController(boardRepo, repo, changes);
    }

    @Test
    public void cannotAddNullList() {
        TaskList l = getList("dummy");
        Board board = getBoard("dummy");
        board.id = 0L;
        l.tasks = null;
        var actual = controller.add(0L, l);
        assertEquals(BAD_REQUEST, actual.getStatusCode());

        l = getList("dummy");
        l.name = null;
        actual = controller.add(0L, l);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void invalidIdTest() {
        var readResponse = controller.getById(-1);
        assertEquals(BAD_REQUEST, readResponse.getStatusCode());
        var deleteResponse = controller.deleteById(-1);
        assertEquals(BAD_REQUEST, deleteResponse.getStatusCode());
        var updateList = getList("dummy");
        updateList.id = -1;
        var updateResponse = controller.update(updateList);
        assertEquals(BAD_REQUEST, updateResponse.getStatusCode());
    }

    @Test
    public void noListWithIdTest() {
        var readResponse = controller.getById(10);
        assertEquals(NOT_FOUND, readResponse.getStatusCode());
        var deleteResponse = controller.deleteById(10);
        assertEquals(BAD_REQUEST, deleteResponse.getStatusCode());
        var updateList = getList("dummy");
        updateList.id = 10;
        var updateResponse = controller.update(updateList);
        assertEquals(NOT_FOUND, updateResponse.getStatusCode());
    }

    @Test
    public void create() {
        var board = getBoard("create");
        boardRepo.save(board);
        var actual = controller.add(0L, getList("create"));
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.hasBody());
    }

    @Test
    public void read() {
        var board = getBoard("fetch");
        boardRepo.save(board);
        var actual = controller.add(0L, getList("fetch")).getBody();
        assertEquals(actual, board);
    }

    @Test
    public void getAllTest() {
        assertEquals(controller.getAll(), new ArrayList<>());
    }

    @Test
    public void update() {
        var board = getBoard("Board");
        boardRepo.save(board);
        TaskList list = getList("update-before");
        var actual = controller.add(0L, list);
        list.id = 0L;

        list.name = "update-after";
        var actual2 = controller.update(list);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
    }

    @Test
    public void updateNull() {
        var actual = controller.update(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void delete() {
        var board = getBoard("delete");
        Board savedBoard = boardRepo.save(board);

        TaskList list = getList("delete");

        var actual = controller.add(savedBoard.id, list);
        if (actual.getBody() == null) return;

        var actual2 = controller.deleteById(0L);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());

        var actual3 = controller.getById(0L);
        assertNull(actual3.getBody());
        assertEquals(NOT_FOUND, actual3.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        var board = getBoard("b1");
        boardRepo.save(board);
        controller.add(0L, getList("l1"));
        assertTrue(boardRepo.calledMethods.contains("save"));
    }

    private TaskList getList(String name) {
        return new TaskList(name);
    }

    private Board getBoard(String name) {
        return new Board(name, name, "");
    }
}
