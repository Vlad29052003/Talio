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


 import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BoardControllerTest {

    private TestBoardRepository repo;

    private BoardController sut;

    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();
        sut = new BoardController(repo);
    }

    @Test
    public void cannotAddNullPerson() {
        var actual = sut.create(getBoard(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void create() {
        var actual = sut.create(getBoard("create"));
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.hasBody());
    }

    @Test
    public void read() {
        var actual = sut.create(getBoard("fetch"));
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;
        var actual2 = sut.read(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void update() {
        Board board = getBoard("updatebefore");
        var actual = sut.create(board);
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;

        board.name = "updateafter";
        board.password = "passwordafter";

        var actual2 = sut.update(id, board);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertEquals(actual.getBody(), actual2.getBody());
    }

    @Test
    public void delete() {
        Board board = getBoard("delete");
        var actual = sut.create(board);
        if(actual.getBody() == null) return;
        long id = actual.getBody().id;

        var actual2 = sut.delete(id);
        assertEquals(HttpStatus.OK, actual2.getStatusCode());
        assertTrue(repo.calledMethods.contains("deleteById"));

        var actual3 = sut.read(id);
        assertNull(actual3.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actual3.getStatusCode());

    }

    @Test
    public void databaseIsUsed() {
        sut.create(getBoard("q1"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    private static Board getBoard(String q) {
        return new Board(q, q, q, false);
    }


}