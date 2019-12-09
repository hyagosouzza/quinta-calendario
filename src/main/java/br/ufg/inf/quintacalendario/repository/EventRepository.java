package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Event;
import org.hibernate.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository extends AbstractRepository<Event> {

    public EventRepository(Session session) {
        super(session);
    }

    @Override
    public List<Event> getByDecription(String description) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from event t where t.description like :description");

        Map<String, Object> parametersQuery = new HashMap<>();

        parametersQuery.put("description", "%" + description + "%");

        return select(jpql.toString(), parametersQuery);
    }

    public List<Event> getByCategory(long idCategory) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.category c ")
                .append("where c.id = :idCategoria");

        Map<String, Object> parametersQuery = new HashMap<>();
        parametersQuery.put("idCategoria", idCategory);

        return select(jpql.toString(), parametersQuery);
    }

    public List<Event> getByInstitute(long idInstitute) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.institutes i ")
                .append("where i.id = :idInstituto");

        Map<String, Object> parametersQuery = new HashMap<>();
        parametersQuery.put("idInstituto", idInstitute);

        return select(jpql.toString(), parametersQuery);
    }

    public List<Event> listarPorRegional(long idRegional) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.regionais r ")
                .append("where r.id = :idRegional");

        Map<String, Object> parametersQuery = new HashMap<>();
        parametersQuery.put("idRegional", idRegional);

        return select(jpql.toString(), parametersQuery);
    }

    public List<Event> getByPeriod(Date initialDate, Date finalDate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t ")
                .append("where t.initialDate >= :initialDate and t.finalDate <= :finalDate");

        Map<String, Object> parametersQuery = new HashMap<>();

        parametersQuery.put("initialDate", initialDate);
        parametersQuery.put("finalDate", finalDate);

        return select(jpql.toString(), parametersQuery);
    }

    public List<Event> getByInitialDate(Date initialDate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t ")
                .append("where t.initialDate = :initialDate");

        Map<String, Object> parametersQuery = new HashMap<>();

        parametersQuery.put("initialDate", initialDate);

        return select(jpql.toString(), parametersQuery);
    }
}
