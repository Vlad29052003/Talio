package server.api;


import commons.Password;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.PasswordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestPasswordRepository implements PasswordRepository {
    public final List<Password> passwords = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Password> findAll() {
        calledMethods.add("findAll");
        return passwords;
    }

    @Override
    public List<Password> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Password> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Password> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public <S extends Password> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Password> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Password> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Password> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Password> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Password> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {

    }

    @Override
    public void deleteAllInBatch() {}

    @Override
    public Password getOne(Long aLong) {
        return null;
    }

    @Override
    public Password getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Password> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Password> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Password> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Password> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Password> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Password> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Password, R> R findBy(Example<S> example,
                                            Function<FluentQuery.FetchableFluentQuery<S>,
                                                    R> queryFunction)
    {
        return null;
    }
    @Override
    public void delete(Password entity) {
    }
    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {}

    @Override
    public void deleteAll(Iterable<? extends Password> entities) {

    }
    @Override
    public void deleteAll() {}
}
