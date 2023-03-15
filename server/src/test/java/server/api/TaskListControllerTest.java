package server.api;

import commons.Board;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class TaskListControllerTest {
    private TaskListTestRepository repo;
    private TaskListController controller;

    @BeforeEach
    public void setup() {
        repo = new TaskListTestRepository();
        controller = new TaskListController(repo);
    }

    @Test
    public void cannotAddNullList() {
        TaskList l = getList("dummy");
        Board board = getBoard("dummy");
        l.tasks = null;
        var actual = controller.add(l, board);
        assertEquals(BAD_REQUEST, actual.getStatusCode());

        l = getList("dummy");
        l.name = null;
        actual = controller.add(l, board);
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
        assertEquals(NOT_FOUND, deleteResponse.getStatusCode());
        var updateList = getList("dummy");
        updateList.id = 10;
        var updateResponse = controller.update(updateList);
        assertEquals(NOT_FOUND, updateResponse.getStatusCode());
    }

    @Test
    public void create() {
        var board = getBoard("create");
        var actual = controller.add(getList("create"), board);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.hasBody());
    }

    @Test
    public void read() {
        var board = getBoard("fetch");
        var actual = controller.add(getList("fetch"), board);
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;
        var actual2 = controller.getById(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void readAll() {
        var board = getBoard("Board");
        var actual = controller.add(getList("List"), board);
        if(actual.getBody() == null) return;
        var actual2 = controller.add(getList("List2"), board);
        if(actual2.getBody() == null) return;

        List<TaskList> allLists = controller.getAll();
        assertEquals(2, allLists.size());
        assertTrue(allLists.contains(actual.getBody()));
        assertTrue(allLists.contains(actual2.getBody()));
    }

    @Test
    public void update() {
        var board = getBoard("Board");
        TaskList list = getList("update-before");
        var actual = controller.add(list, board);
        if (actual.getBody() == null) return;
        list.id = actual.getBody().id;

        list.name = "update-after";
        var actual2 = controller.update(list);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void updateNull() {
        var actual = controller.update(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void delete() {
        var board = getBoard("delete");
        TaskList list = getList("delete");
        var actual = controller.add(list, board);
        if (actual.getBody() == null) return;
        long id = actual.getBody().id;

        var actual2 = controller.deleteById(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertTrue(repo.calledMethods.contains("deleteById"));

        var actual3 = controller.getById(id);
        assertNull(actual3.getBody());
        assertEquals(NOT_FOUND, actual3.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        var board = getBoard("b1");
        controller.add(getList("l1"), board);
        assertTrue(repo.calledMethods.contains("save"));
    }

    private TaskList getList(String name) {
        return new TaskList(name, 0);
    }

    private Board getBoard(String name) {
        return new Board(name, name);
    }
}
