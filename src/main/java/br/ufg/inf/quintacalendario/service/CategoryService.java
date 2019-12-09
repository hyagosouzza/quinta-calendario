package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.model.Category;
import br.ufg.inf.quintacalendario.repository.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Service responsible for validating and communicating with the CategoryRepository.
 *
 * @author Joao Pedro Pinheiro
 */
public class CategoryService {

    private static final Integer MIN_LENGTH = 4;
    private SessionFactory sessionFactory;

    /**
     * Class's default constructor
     * @param sessionFactory entity's SessionFactory
     */
    public CategoryService(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    /**
     * Persist the object into the Database
     * @param category category to be persisted
     * @return true if the operation was successful or false if it wasn't
     */
    public boolean save(Category category) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            validateCategory(category);

            new CategoryRepository(session).save(category);
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
     * Validate a single instance of Category
     * @param category category to be validated
     * @throws IllegalArgumentException Validation unsuccessful
     */
    private void validateCategory(Category category) {
        if (category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria nao pode ser vazio");
        }

        if ((category.getName().trim().length()) < MIN_LENGTH) {
            throw new IllegalArgumentException("O node da categoria deve ter no minimo " + MIN_LENGTH + " caracteres");
        }
    }

    /**
     * List all categories
     * @return a list of categories
     */
    public List<Category> getRecords() {
        Session session = sessionFactory.openSession();
        return new CategoryRepository(session).get();
    }

    /**
     * Delete all categories in the database
     */
    public void truncateTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new CategoryRepository(session).dropTable();
        transaction.commit();
        session.close();
    }

    /**
     * List categories by description
     * @param name name to be searched by
     * @return a list of categories
     */
    public List<Category> getRecordsByName(String name) {
        Session session = sessionFactory.openSession();
        return new CategoryRepository(session).getByName(name);
    }

    /**
     * Get single category by id
     * @param id id to be searched by
     * @return a Category
     */
    public Category getById(Integer id) {
        Session session = sessionFactory.openSession();
        return new CategoryRepository(session).getById(id);
    }

    /**
     * Edit one instance of category in the database
     * @param id id of the category to be edited
     * @param name new category's name
     */
    public void editName(Integer id, String name) {
        Session session = sessionFactory.openSession();
        CategoryRepository repository = new CategoryRepository(session);
        Category category = repository.getById(id);

        Transaction transaction = session.beginTransaction();

        category.setName(name);
        repository.update(category);

        transaction.commit();
        session.close();
    }

    /**
     * Delete a single category in the database
     * @param id id of the category to be deleted
     */
    public void remove(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new CategoryRepository(session).remover(id);
        transaction.commit();
        session.close();
    }
}
