package server.api;

import commons.TaskList;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.TaskListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("NullableProblems")
public class TaskListTestRepository implements TaskListRepository {
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
        return null;
    }

    @Override
    public Page<TaskList> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TaskList> findAllById(Iterable<Long> ids) {
        call("findAllById");
        List<TaskList> lists = new ArrayList<>();
        for (Long id : ids) {
            Optional<TaskList> taskList = find(id);
            taskList.ifPresent(lists::add);
        }
        return lists;
    }

    @Override
    public long count() {
        return lists.size();
    }

    @Override
    public void deleteById(Long id) {
        call("deleteById");
        find(id).map(lists::remove);
    }

    @Override
    public void delete(TaskList entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TaskList> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TaskList> S save(S entity) {
        call("save");
        boolean present = lists.stream().anyMatch(e -> e.id == entity.id);
        if(present){
            lists.replaceAll(t -> t.id == entity.id ? entity : t);
        }else{
            entity.id = lists.size();
            lists.add(entity);
        }
        return entity;
    }

    @Override
    public <S extends TaskList> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TaskList> findById(Long id) {
        call("findById");
        return find(id);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TaskList> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public <S extends TaskList> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TaskList> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TaskList getOne(Long aLong) {
        return null;
    }

    @Override
    public TaskList getById(Long aLong) {
        return find(aLong).orElse(null);
    }

    @Override
    public <S extends TaskList> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TaskList> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TaskList> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TaskList> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TaskList> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TaskList> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TaskList, R> R findBy(Example<S> example,
                                            Function<FluentQuery.FetchableFluentQuery<S>, R>
                                                    queryFunction) {
        return null;
    }

    private Optional<TaskList> find(long id) {
        for (TaskList list : lists) {
            if (list.id == id) {
                return Optional.of(list);
            }
        }

        return Optional.empty();
    }
}
