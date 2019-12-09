package br.ufg.inf.quintacalendario.controller;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Category;
import br.ufg.inf.quintacalendario.model.Event;
import br.ufg.inf.quintacalendario.model.Institute;
import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.service.CategoryService;
import br.ufg.inf.quintacalendario.service.EventService;
import br.ufg.inf.quintacalendario.service.InstituteService;
import br.ufg.inf.quintacalendario.service.RegionalService;
import br.ufg.inf.quintacalendario.view.console.EventScreenConsole;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Controller responsible for intercepting event entity operations, extend AbstractController class
 *
 * @author Hyago Souza
 * @see AbstractController
 */
public class EventController extends AbstractController {

    private EventScreenConsole eventScreen;

    /**
     * Constructor's class
     */
    public EventController() {
        super(Application.getInstance().getSessionFactory());
        setEventScreen(new EventScreenConsole(System.out, this));
    }

    /**
     * Register a new event with received params
     *
     * @param description description of event to create
     * @param title       title of event to create
     * @param initialDate initial date of event to create
     * @param finalDate   final date of event to create
     * @param categoryId  category identifier of event to create
     * @param regionalId  regional identifier of event to create
     * @param instituteId institute identifier of event to create
     * @return boolean representing success or error
     */
    public boolean register(String description,
                            String title,
                            String initialDate,
                            String finalDate,
                            Integer categoryId,
                            Integer regionalId,
                            Integer instituteId
    ) {
        try {
            Event event = new Event();

            event.setDescription(description);
            event.setTitle(title);

            Date data = from(initialDate);
            event.setInitialDate(data);

            data = from(finalDate);
            event.setFinalDate(data);

            event.setCategory(new CategoryService(getAbstractSessionFactory()).getById(categoryId));

            List<Institute> institutes = new ArrayList<>();
            institutes.add(new InstituteService(getAbstractSessionFactory()).getById(instituteId));

            event.setInstitutes(institutes);

            List<Regional> regionals = new ArrayList<>();
            regionals.add(new RegionalService(getAbstractSessionFactory()).getById(regionalId));

            event.setRegionals(regionals);

            EventService eventService = new EventService(getAbstractSessionFactory());
            eventService.save(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Show category options on screen
     */
    void showHisOptions() {
        getEventScreen().displayOptions();
    }

    /**
     * Convert a string to date
     *
     * @param date string representing a date
     * @return Date converted date
     * @throws ParseException
     */
    private Date from(String date) {
        if (Objects.isNull(date) || date.isEmpty()) {
            return null;
        }

        Date newDate = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            newDate = formatter.parse(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return newDate;
    }

    /**
     * Returns all event records on database
     *
     * @return list of records
     */
    public List<Event> listRecords() {
        EventService eventService = new EventService(getAbstractSessionFactory());
        return eventService.getRecords();
    }

    /**
     * Returns all event records on database filtering by their description
     *
     * @param description field used on query filter
     * @return list of records filtered by description
     */
    public List<Event> listRecordsByDescription(String description) {
        EventService eventService = new EventService(getAbstractSessionFactory());
        return eventService.getRecordsByDescription(description);
    }

    /**
     * Returns a single event record on database filtering by your id
     *
     * @param id event identifier
     * @return event with specified identifier
     * @see Event
     */
    public Event getById(Integer id) {
        EventService eventService = new EventService(getAbstractSessionFactory());
        return eventService.getById(id);
    }

    /**
     * Returns a list of event records filtering by your dates
     *
     * @param initialDate initial date of event
     * @param finalDate   final date of event
     * @return records with matching dates
     * @see List
     */
    public List<Event> listByPeriod(String initialDate, String finalDate) {
        EventService eventService = new EventService(getAbstractSessionFactory());
        return eventService.getByPeriod(from(initialDate), from(finalDate));
    }

    /**
     * Returns all regional records on database
     *
     * @return list of records
     */
    public List<Regional> listRegionals() {
        RegionalService regionalService = new RegionalService(getAbstractSessionFactory());
        return regionalService.getRecords();
    }

    /**
     * Returns all institute records on database
     *
     * @return list of records
     */
    public List<Institute> listInstitutes() {
        InstituteService instituteService = new InstituteService(getAbstractSessionFactory());
        return instituteService.getRecords();
    }

    /**
     * Returns all category records on database
     *
     * @return list of records
     */
    public List<Category> listCategories() {
        CategoryService categoryService = new CategoryService(getAbstractSessionFactory());
        return categoryService.getRecords();
    }

    /**
     * Returns screen console of event entity
     *
     * @return event screen console
     * @see EventScreenConsole
     */
    public EventScreenConsole getEventScreen() {
        return eventScreen;
    }

    /**
     * Attribute a event screen console to entity
     *
     * @param eventScreen event screen console
     * @see EventScreenConsole
     */
    public void setEventScreen(EventScreenConsole eventScreen) {
        this.eventScreen = eventScreen;
    }
}
