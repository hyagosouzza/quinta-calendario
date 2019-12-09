package br.ufg.inf.quintacalendario.repository;

import java.util.List;

public interface IRepository<T> {
    public void save(T t);

    public void update(T t);

    public List<T> get();

    public List<T> getByDecription(String description);

    public T getById(long id);

    public void remover(long id);

    public List<T> select(String jpql);

    public void dropTable();


}
