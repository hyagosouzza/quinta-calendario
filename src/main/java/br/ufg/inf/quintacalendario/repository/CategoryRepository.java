package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Category;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository extends AbstractRepository<Category> {

    public CategoryRepository(Session session) {
        super(session);
    }

    @Override
    public List<Category> getByName(String name) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from category t where t.name like :name");

        Map<String, Object> parametersQuery = new HashMap<>();
        parametersQuery.put("name", "%" + name + "%");

        return select(jpql.toString(), parametersQuery);
    }
}
