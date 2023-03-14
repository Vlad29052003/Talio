package server.api;

import commons.TaskList;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestTaskListRepository implements ListRepository {

    public final List<TaskList> lists = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<TaskList> findAll() {
        call("findAll");
        return lists;
    }

    @Override
    public List<TaskList> findAll(Sort sort) {
        call("findAll");
        return null;
    }

    @Override
    public Page<TaskList> findAll(Pageable pageable) {
        call("findAll");
        return null;
    }

    @Override
    public List<TaskList> findAllById(Iterable<Long> longs) {
        call("findAllById");
        return null;
    }

    @Override
    public long count() {
        call("count");
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        call("deleteById");
    }

    @Override
    public void delete(TaskList entity) {
        call("delete");
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        call("deleteAllById");
    }

    @Override
    public void deleteAll(Iterable<? extends TaskList> entities) {
        call("deleteAll");
    }

    @Override
    public void deleteAll() {
        call("deleteAll");
    }

    @Override
    public <S extends TaskList> S save(S entity) {
        call("save");
        return null;
    }

    @Override
    public <S extends TaskList> List<S> saveAll(Iterable<S> entities) {
        call("saveAll");
        return null;
    }

    @Override
    public Optional<TaskList> findById(Long aLong) {
        call("findById");
        return lists.stream().filter(l -> l.id == aLong).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        return lists.stream().anyMatch(l -> l.id == aLong);
    }

    @Override
    public void flush() {
        call("flush");
    }

    @Override
    public <S extends TaskList> S saveAndFlush(S entity) {
        call("saveAndFlush");
        return null;
    }

    @Override
    public <S extends TaskList> List<S> saveAllAndFlush(Iterable<S> entities) {
        call("saveAndFlush");
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TaskList> entities) {
        call("deleteAllInBatch");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        call("deleteAllByIdInBatch");
    }

    @Override
    public void deleteAllInBatch() {
        call("deleteAllInBatch");
    }

    @Override
    public TaskList getOne(Long aLong) {
        call("getOne");
        return null;
    }

    @Override
    public TaskList getById(Long aLong) {
        call("getById");
        return null;
    }

    @Override
    public <S extends TaskList> Optional<S> findOne(Example<S> example) {
        call("findOne");
        return Optional.empty();
    }

    @Override
    public <S extends TaskList> List<S> findAll(Example<S> example) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends TaskList> List<S> findAll(Example<S> example, Sort sort) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends TaskList> Page<S> findAll(Example<S> example, Pageable pageable) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends TaskList> long count(Example<S> example) {
        call("count");
        return 0;
    }

    @Override
    public <S extends TaskList> boolean exists(Example<S> example) {
        call("exists");
        return false;
    }

    @Override
    public <S extends TaskList, R> R findBy(Example<S> example,
                                            Function
                                                    <FluentQuery.FetchableFluentQuery<S>, R>
                                                    queryFunction) {
        call("findBy");
        return null;
    }
}
