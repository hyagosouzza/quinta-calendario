package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.model.Institute;
import br.ufg.inf.quintacalendario.repository.InstituteRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Service responsible for validating and communicating with the InstituteRepository.
 *
 * @author Joao Pedro Pinheiro
 */
public class InstituteService {

    private static final Integer MIN_LENGTH = 4;
    private SessionFactory sessionFactory;

    /**
     * Class's default constructor
     * @param sessionFactory entity's SessionFactory
     */
    public InstituteService(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    /**
     * Persist the object into the Database
     * @param institute institute to be persisted
     * @return true if the operation was successful or false if it wasn't
     */
    public boolean save(Institute institute) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            validadeInstitute(institute);

            new InstituteRepository(session).save(institute);
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
     * Validate a single instance of Institute
     * @param institute institute to be validated
     * @throws IllegalArgumentException Validation unsuccessful
     */
    private void validadeInstitute(Institute institute) {
        if (institute.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do instituto nao pode ser vazio");
        }

        if ((institute.getName().trim().length()) < MIN_LENGTH) {
            throw new IllegalArgumentException("O nome do instituto deve ter no minimo 4 caracteres");
        }
    }

    /**
     * List all institutes
     * @return a list of institutes
     */
    public List<Institute> getRecords() {
        Session session = sessionFactory.openSession();
        return new InstituteRepository(session).get();
    }

    /**
     * Delete all records in the database
     */
    public void truncateTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new InstituteRepository(session).dropTable();
        transaction.commit();
        session.close();
    }

    /**
     * List institutes by description
     * @param description description to be searched by
     * @return a list of institutes
     */
    public List<Institute> getRecordsByDescription(String description) {
        Session session = sessionFactory.openSession();
        return new InstituteRepository(session).getByDecription(description);
    }

    /**
     * List institutes by id
     * @param id id to be searched by
     * @return a list of institutes
     */
    public Institute getById(Integer id) {
        Session session = sessionFactory.openSession();
        return new InstituteRepository(session).getById(id);
    }

    /**
     * Edit one instance of institute in the database
     * @param id id of the institute to be edited
     * @param name new institute's name
     */
    public void edit(Integer id, String name) {
        Session session = sessionFactory.openSession();
        InstituteRepository instituteRepository = new InstituteRepository(session);
        Institute institute = instituteRepository.getById(id);

        Transaction transaction = session.beginTransaction();

        institute.setName(name);
        instituteRepository.update(institute);

        transaction.commit();
        session.close();
    }

    /**
     * Remove a single institute from the database
     * @param id id of the institute to be removed
     */
    public void remove(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new InstituteRepository(session).remover(id);
        transaction.commit();
        session.close();
    }
}
