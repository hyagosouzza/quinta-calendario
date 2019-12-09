package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.model.Event;
import br.ufg.inf.quintacalendario.repository.EventRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Service responsible for validating and communicating with the EventRepository.
 *
 * @author Joao Pedro Pinheiro
 */
public class EventService {

    private SessionFactory sessionFactory;

    /**
     * Class's default constructor
     * @param sessionFactory entity's SessionFactory
     */
    public EventService(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    /**
     * Persist the object into the Database
     * @param event event to be persisted
     * @return true if the operation was successful or false if it wasn't
     */
    public boolean save(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            new EventRepository(session).save(event);
            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Edit one instance of event in the database
     * @param event event to be edited
     */
    public void edit(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        EventRepository eventRepository = new EventRepository(session);
        eventRepository.update(event);
        transaction.commit();
    }

    /**
     * List all events
     * @return a list of events
     */
    public List<Event> getRecords() {
        Session session = sessionFactory.openSession();
        return new EventRepository(session).get();
    }

    /**
     * List events by description
     * @param description description to be searched by
     * @return a list of Events
     */
    public List<Event> getRecordsByDescription(String description) {
        Session session = sessionFactory.openSession();
        return new EventRepository(session).getByName(description);
    }

    /**
     * Get a single event by id
     * @param id id of the event to be searched
     * @return a Event
     */
    public Event getById(long id) {
        Session session = sessionFactory.openSession();
        return new EventRepository(session).getById(id);
    }

    /**
     * List events by a given period
     * @param startDate start date of the period
     * @param endDate end date of the period
     * @return a list of Events
     */
    public List<Event> getByPeriod(Date startDate, Date endDate) {
        Session session = sessionFactory.openSession();
        return new EventRepository(session).getByPeriod(startDate, endDate);
    }

    /**
     * List events by a single date
     * @param date date to be searched by
     * @return a list of Events
     */
    public List<Event> getByDate(Date date) {
        Session session = sessionFactory.openSession();
        return new EventRepository(session).getByInitialDate(date);

    }

    /**
     * Delete all records in the database
     */
    public void truncateTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        new EventRepository(session).dropTable();
        transaction.commit();
        session.close();
    }

    /**
     * Clears an event category
     * @param event event to be edited
     */
    private void clearCategory(Event event) {
        event.setCategory(null);
    }

    /**
     * Clears all the event's institutes
     * @param event event to be edited
     */
    private void clearInstitutes(Event event) {
        event.getInstitutes().clear();
    }

    /**
     * Clears all the event's regionals
     * @param event event to be edited
     */
    private void clearRegionals(Event event) {
        event.getRegionals().clear();
    }

    /**
     * Clears all attributes of an given event
     * @param event event to be cleared
     */
    public void clearAttributes(Event event) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            clearCategory(event);
            clearInstitutes(event);
            clearRegionals(event);

            new EventRepository(session).update(event);
            transaction.commit();
        }
    }

    /**
     * Get the session factory
     * @return a session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set the session factory
     * @param sessionFactory a session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
