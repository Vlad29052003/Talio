package server.api;

import commons.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestTaskRepository implements TaskRepository {

    public final List<Task> tasks = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Task> findAll() {
        call("findAll");
        return tasks;

    }

    @Override
    public List<Task> findAll(Sort sort) {
        call("findAll");
        return tasks;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        call("findAll");
        return null;
    }

    @Override
    public List<Task> findAllById(Iterable<Long> longs) {
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
        tasks.removeIf((t) -> t.id == aLong);
    }

    @Override
    public void delete(Task entity) {
        call("delete");
        deleteById(entity.id);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        call("deleteAllById");
    }

    @Override
    public void deleteAll(Iterable<? extends Task> entities) {
        call("deleteAll");
    }

    @Override
    public void deleteAll() {
        call("deleteAll");
    }

    @Override
    public <S extends Task> S save(S entity) {
        call("save");
        boolean present = tasks.stream().anyMatch(e -> e.id == entity.id);
        if(present){
            tasks.replaceAll(t -> t.id == entity.id ? entity : t);
        }else{
            entity.id = tasks.size();
            tasks.add(entity);
        }
        return entity;
    }

    @Override
    public <S extends Task> List<S> saveAll(Iterable<S> entities) {
        call("saveAll");
        return null;
    }

    @Override
    public Optional<Task> findById(Long aLong) {
        call("findById");
        return tasks.stream().filter(t -> t.id == aLong).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        if (tasks.stream().filter(t -> t.id == aLong).findFirst().isEmpty())
            return false;
        return true;
    }

    @Override
    public void flush() {
        call("flush");
    }

    @Override
    public <S extends Task> S saveAndFlush(S entity) {
        call("saveAndFlush");
        return save(entity);
    }

    @Override
    public <S extends Task> List<S> saveAllAndFlush(Iterable<S> entities) {
        call("saveAllAndFlush");
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Task> entities) {
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
    public Task getOne(Long aLong) {
        call("getOne");
        return null;
    }

    @Override
    public Task getById(Long aLong) {
        call("getById");
        return null;
    }

    @Override
    public <S extends Task> Optional<S> findOne(Example<S> example) {
        call("findOne");
        return Optional.empty();
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example, Sort sort) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable) {
        call("findAll");
        return null;
    }

    @Override
    public <S extends Task> long count(Example<S> example) {
        call("count");
        return 0;
    }

    @Override
    public <S extends Task> boolean exists(Example<S> example) {
        call("exists");
        return false;
    }

    @Override
    public <S extends Task, R> R findBy(Example<S> example,
                                        Function
                                                <FluentQuery.FetchableFluentQuery<S>,
                                                        R> queryFunction) {
        call("findBy");
        return null;
    }
}
