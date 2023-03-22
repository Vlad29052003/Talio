/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BoardControllerTest {

    private TestBoardRepository repo;

    private BoardController sut;
    private BoardChangeQueue changes;

    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();
        changes = new BoardChangeQueue();
        sut = new BoardController(repo, changes);
    }

    @Test
    public void cannotAddNullBoard() {
        Board b = getBoard("dummy");
        b.lists = null;
        var actual = sut.createBoard(b);
        assertEquals(BAD_REQUEST, actual.getStatusCode());

        b = getBoard("dummy");
        b.name = null;
        actual = sut.createBoard(b);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void invalidIdTest() {
        var readResponse = sut.getBoard(-1);
        assertEquals(BAD_REQUEST, readResponse.getStatusCode());
        var deleteResponse = sut.deleteBoard(-1);
        assertEquals(BAD_REQUEST, deleteResponse.getStatusCode());
        var updateResponse = sut.updateBoard(-1, getBoard("Dummy"));
        assertEquals(BAD_REQUEST, updateResponse.getStatusCode());
    }

    @Test
    public void noBoardWithIdTest() {
        var readResponse = sut.getBoard(10);
        assertEquals(NOT_FOUND, readResponse.getStatusCode());
        var deleteResponse = sut.deleteBoard(10);
        assertEquals(NOT_FOUND, deleteResponse.getStatusCode());
        var updateResponse = sut.updateBoard(10, getBoard("dummy"));
        assertEquals(NOT_FOUND, updateResponse.getStatusCode());
    }


    @Test
    public void create() {
        var actual = sut.createBoard(getBoard("create"));
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.hasBody());
    }

    @Test
    public void read() {
        var actual = sut.createBoard(getBoard("fetch"));
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;
        var actual2 = sut.getBoard(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void readAll(){
        var actual = sut.createBoard(getBoard("Board"));
        if(actual.getBody() == null) return;
        var actual2 = sut.createBoard(getBoard("Board2"));
        if(actual2.getBody() == null) return;

        List<Board> allBoards = sut.getAll();
        assertEquals(2, allBoards.size());
        assertTrue(allBoards.contains(actual.getBody()));
        assertTrue(allBoards.contains(actual2.getBody()));
    }

    @Test
    public void update() {
        Board board = getBoard("updatebefore");
        var actual = sut.createBoard(board);
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;

        board.name = "updateafter";
        board.password = "passwordafter";
        board.RWpermission = true;
        board.backgroundColor = "otherColor";

        var actual2 = sut.updateBoard(id, board);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void updateNull() {
        var actual = sut.updateBoard(10, null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void delete() {
        Board board = getBoard("delete");
        var actual = sut.createBoard(board);
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;

        var actual2 = sut.deleteBoard(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertTrue(repo.calledMethods.contains("deleteById"));

        var actual3 = sut.getBoard(id);
        assertNull(actual3.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actual3.getStatusCode());

    }

    @Test
    public void databaseIsUsed() {
        sut.createBoard(getBoard("q1"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    private static Board getBoard(String q) {
        return new Board(q, q, q, false);
    }


}