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
    public List<Category> getByDecription(String description) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from category t where t.name like :description");

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("description", "%" + description + "%");

        return select(jpql.toString(), parametros);
    }
}
