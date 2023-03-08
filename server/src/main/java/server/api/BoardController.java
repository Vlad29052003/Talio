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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.BoardRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardRepository repo;

    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Board> getAll() {
        return repo.findAll();
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> create(@RequestBody Board board) {

        if (board.lists == null
            || board.tags == null
            || isNullOrEmpty(board.name)
            || isNullOrEmpty(board.backgroundColor)
            || isNullOrEmpty(board.password)){
            return ResponseEntity.badRequest().build();
        }

        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> read(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Board> board = repo.findById(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = { "", "/{id}" })
    public ResponseEntity<Board> update(@PathVariable("id") long id, @RequestBody Board board) {
        Optional<Board> optLocalBoard = repo.findById(id);
        if(optLocalBoard.isEmpty()) return ResponseEntity.notFound().build();

        Board localBoard = optLocalBoard.get();
        localBoard.name = board.name;
        localBoard.password = board.password;
        localBoard.backgroundColor = board.backgroundColor;

        Board saved = repo.save(localBoard);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping(path = { "", "/{id}" })
    public ResponseEntity<Board> delete(@PathVariable("id") long id) {

        if(!repo.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}