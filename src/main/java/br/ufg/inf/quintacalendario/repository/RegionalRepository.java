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
        jpql.append("Select t from regional t where t.nome like :description");

        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("description", "%" + description + "%");
        
        return select(jpql.toString(), parametros);
    }
}
