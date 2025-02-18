package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.repository.RegionalRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Service responsible for validating and communicating with the RegionalRepository.
 *
 * @author Joao Pedro Pinheiro
 */
public class RegionalService {

    private static final Integer MIN_LENGTH = 4;
    private SessionFactory sessionFactory;

    /**
     * Class's default constructor
     * @param sessionFactory entity's SessionFactory
     */
    public RegionalService(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    /**
     * Persist the object into the Database
     * @param regional regional to be persisted
     * @return true if the operation was successful or false if it wasn't
     */
    public boolean save(Regional regional) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            validateRegional(regional);

            new RegionalRepository(session).save(regional);
            transaction.commit();

            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Edit one instance of regional in the database
     * @param id id of the regional to be edited
     * @param description new regional's description
     */
    public void editDescription(long id, String description) {
        Session session = sessionFactory.openSession();
        RegionalRepository regionalRepository = new RegionalRepository(session);
        Regional regional = regionalRepository.getById(id);

        Transaction transaction = session.beginTransaction();

        regional.setName(description);
        regionalRepository.update(regional);

        transaction.commit();
        session.close();
    }

    /**
     * Validate a single instance of Regional
     * @param regional regional to be validated
     * @throws IllegalArgumentException Validation unsuccessful
     */
    private void validateRegional(Regional regional) {
        if (regional.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da regional nao pode ser vazio");
        }

        if ((regional.getName().trim().length()) < MIN_LENGTH) {
            throw new IllegalArgumentException("O node da regional deve ter no minimo 4 caracteres");
        }
    }

    /**
     * List all regionals
     * @return a list of regionals
     */
    public List<Regional> getRecords() {
        Session session = sessionFactory.openSession();
        return new RegionalRepository(session).get();
    }

    /**
     * List regionals by description
     * @param description description to be searched by
     * @return a list of regionals
     */
    public List<Regional> getRecordsByDescription(String description) {
        Session session = sessionFactory.openSession();
        return new RegionalRepository(session).getByName(description);
    }

    /**
     * List regionals by id
     * @param id id to be searched by
     * @return a list of regionals
     */
    public Regional getById(long id) {
        Session session = sessionFactory.openSession();
        return new RegionalRepository(session).getById(id);
    }

    /**
     * Delete all regionals in the database
     */
    public void truncateTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new RegionalRepository(session).dropTable();
        transaction.commit();
        session.close();
    }

    /**
     * Delete a single regional from the database
     * @param id id of the regional to be deleted
     */
    public void remove(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new RegionalRepository(session).remover(id);
        transaction.commit();
        session.close();
    }
}
