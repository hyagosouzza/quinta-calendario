package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.controller.CategoryController;
import br.ufg.inf.quintacalendario.controller.EventController;
import br.ufg.inf.quintacalendario.controller.InstituteController;
import br.ufg.inf.quintacalendario.controller.RegionalController;
import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Event;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EventServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    private SessionFactory sessionFactory;

    private EventService eventServiceUnderTest;

    private final String description = "Creating Event";
    private final String title = "Festinha";
    private final String initialDate = "28/01/1999";
    private final String finalDate = "30/01/1999";
    private final Integer categoryId = 1;
    private final Integer regionalId = 1;
    private final Integer instituteId = 1;

    private final RegionalController regionalController = new RegionalController();
    private final CategoryController categoryController = new CategoryController();
    private final InstituteController instituteController = new InstituteController();
    private final EventController eventController = new EventController();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

        sessionFactory = Application.getInstance().getSessionFactory();
        eventServiceUnderTest = new EventService(sessionFactory);

        limparObjetoEvento();
        new RegionalService(sessionFactory).truncateTable();
        new EventService(sessionFactory).truncateTable();
        new InstituteService(sessionFactory).truncateTable();
        new CategoryService(sessionFactory).truncateTable();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        new EventService(sessionFactory).truncateTable();
    }

    @Test
    public void testRegisterSuccessfully() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(finalDate);

        Event event = new Event();
        event.setDescription(description);
        event.setTitle(title);
        event.setFinalDate(date);
        Assert.assertTrue(eventServiceUnderTest.save(event));
    }

    @Test
    public void testListRecords() {
        generateTwoRandomEvents();
        List<Event> events = eventServiceUnderTest.getRecords();

        List<Event> eventsByDescription = eventServiceUnderTest.getRecordsByDescription(description);

        assertEquals(2, events.size());
        assertEquals(events.get(0).getId(), eventsByDescription.get(0).getId());
    }

    @Test
    public void testListByPeriod() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date startDate = dateFormat.parse("30/03/2002");
        Date endDate = dateFormat.parse("30/04/2002");

        generateTwoRandomEvents();
        List<Event> noOneEvents = eventServiceUnderTest.getByPeriod(startDate, endDate);

        startDate = dateFormat.parse(initialDate);
        endDate = dateFormat.parse("30/04/2002");
        List<Event> events = eventServiceUnderTest.getByPeriod(startDate, endDate);

        assertEquals(2, events.size());
        assertEquals(0, noOneEvents.size());
    }

    @Test
    public void testListByIdReturnsNoOne() {
        Event event = eventServiceUnderTest.getById(409);

        assertNull(event);
    }

    @Test
    public void testListByDescriptionReturnsEmptyList() {
        List<Event> events = eventServiceUnderTest.getRecordsByDescription("409");

        assertEquals(Collections.emptyList(), events);
    }

    public void generateTwoRandomEvents() {
        eventController.register(description, title, initialDate, finalDate, categoryId, regionalId, instituteId);
        eventController.register("Another description", title, initialDate, finalDate, categoryId, regionalId, instituteId);
    }

    public void generateRandomRegional() {
        regionalController.register("Regional to this test");
    }

    public void generateRandomCategory() {
        categoryController.register("Category to this test");
    }

    public void generateRandomInstitute() {
        instituteController.register("Institute to this test");
    }

    private void limparObjetoEvento() {
        EventService eventService = new EventService(sessionFactory);
        List<Event> events = eventService.getRecords();

        events.forEach(eventService::clearAttributes);
    }
}
