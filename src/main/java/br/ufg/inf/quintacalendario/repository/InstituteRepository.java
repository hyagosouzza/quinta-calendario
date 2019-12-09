package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Institute;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstituteRepository extends AbstractRepository<Institute> {

    public InstituteRepository(Session session) {
        super(session);
    }

    @Override
    public List<Institute> getByName(String description) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from institute t where t.name like :description");

        Map<String, Object> parametersQuery = new HashMap<>();

        parametersQuery.put("description", "%" + description + "%");

        return select(jpql.toString(), parametersQuery);
    }

}
