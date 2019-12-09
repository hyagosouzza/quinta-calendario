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
        jpql.append("Select t from event t where t.descricao like :descricao");

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("descricao", "%" + description + "%");

        return select(jpql.toString(), parametros);
    }

    public List<Event> getByCategory(long idCategory) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.category c ")
                .append("where c.id = :idCategoria");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idCategoria", idCategory);

        return select(jpql.toString(), parametros);
    }

    public List<Event> getByInstitute(long idInstitute) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.institutes i ")
                .append("where i.id = :idInstituto");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idInstituto", idInstitute);

        return select(jpql.toString(), parametros);
    }

    public List<Event> listarPorRegional(long idRegional) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t inner join t.regionais r ")
                .append("where r.id = :idRegional");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idRegional", idRegional);

        return select(jpql.toString(), parametros);
    }

    public List<Event> getByPeriod(Date initialDate, Date finalDate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t ")
                .append("where t.dataInicial >= :dataInicial and t.dataFinal <= :dataFinal");

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("dataInicial", initialDate);
        parametros.put("dataFinal", finalDate);

        return select(jpql.toString(), parametros);
    }

    public List<Event> getByInitialDate(Date initialDate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from event t ")
                .append("where t.dataInicial = :dataInicial");

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("dataInicial", initialDate);

        return select(jpql.toString(), parametros);
    }
}
