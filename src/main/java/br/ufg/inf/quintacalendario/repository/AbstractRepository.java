package br.ufg.inf.quintacalendario.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> implements IRepository<T> {

    private final Class<T> modelClass;
    protected Session session;

    @SuppressWarnings("unchecked")
    public AbstractRepository(Session session) {
        super();
        this.session = session;
        this.modelClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public String getModelAlias() {
        return String.valueOf(this.modelClass.getSimpleName().charAt(0))
                .toLowerCase();
    }

    @Override
    public void save(T t) {
        session.save(t);
    }

    @Override
    public void update(T t) {
        session.merge(t);
    }

    @Override
    public List<T> get() {
        String sql = "SELECT " + getModelAlias() + " FROM "
                + this.modelClass.getSimpleName().toLowerCase() + " " + getModelAlias();

        return session.createQuery(sql, modelClass).getResultList();
    }

    @Override
    public T getById(long id) {
        return session.find(modelClass, id);
    }

    @Override
    public List<T> select(String jpql) {
        return session.createQuery(jpql, modelClass).getResultList();
    }

    public List<T> select(String jpql, Map<String, Object> parametersQuery) {
        Query<T> query = session.createQuery(jpql, modelClass);

        for (Map.Entry<String, Object> parameter : parametersQuery.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }

        return query.getResultList();
    }

    @Override
    public void dropTable() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Delete from " + this.modelClass.getSimpleName().toLowerCase());

        Query<?> query = session.createQuery(jpql.toString());
        query.executeUpdate();

    }

    @Override
    public void remover(long id) {
        T t = getById(id);
        session.remove(t);
    }
}
