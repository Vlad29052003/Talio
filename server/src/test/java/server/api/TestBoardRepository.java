package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import commons.Board;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import server.database.BoardRepository;

public class TestBoardRepository implements BoardRepository {

    public final List<Board> quotes = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Board> findAll() {
        calledMethods.add("findAll");
        return quotes;
    }

    @Override
    public List<Board> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Board> findAllById(Iterable<Long> ids) {
        calledMethods.add("findAllById");
        List<Board> boards = new ArrayList<>();
        for (Long id : ids) {
            Optional<Board> board = find(id);
            board.ifPresent(boards::add);
        }
        return boards;
    }

    @Override
    public <S extends Board> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Board> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Board> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Board> entities) {}

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {

    }

    @Override
    public void deleteAllInBatch() {}

    @Override
    public Board getOne(Long id) {
        return null;
    }

    @Override
    public Board getById(Long id) {
        call("getById");
        return find(id).orElseThrow();
    }

    private Optional<Board> find(Long id) {
        return quotes.stream().filter(q -> q.id == id).findFirst();
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Board> S save(S entity) {
        call("save");
        entity.id = quotes.size();
        quotes.add(entity);
        return entity;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return find(id);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public long count() {
        return quotes.size();
    }

    @Override
    public void deleteById(Long id) {
        call("deleteById");
        find(id).map(quotes::remove);
    }

    @Override
    public void delete(Board entity) {}

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {}

    @Override
    public void deleteAll(Iterable<? extends Board> entities) {}

    @Override
    public void deleteAll() {}

    @Override
    public <S extends Board> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Board> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Board> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Board> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Board, R> R findBy(
        Example<S> example,
        Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}