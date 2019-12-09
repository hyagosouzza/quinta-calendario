package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Regional;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionalRepository extends AbstractRepository<Regional> {

    public RegionalRepository(Session session) {
        super(session);
    }

    @Override
    public List<Regional> getByDecription(String description) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from regional t where t.name like :description");

        Map<String, Object> parametersQuery = new HashMap<>();

        parametersQuery.put("description", "%" + description + "%");

        return select(jpql.toString(), parametersQuery);
    }
}
