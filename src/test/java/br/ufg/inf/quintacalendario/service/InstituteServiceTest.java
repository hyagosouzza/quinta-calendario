package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Event;
import br.ufg.inf.quintacalendario.model.Institute;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InstituteServiceTest {

    private InstituteService instituteServiceUnderTest;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static SessionFactory sessionFactory;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

        sessionFactory = Application.getInstance().getSessionFactory();
        instituteServiceUnderTest = new InstituteService(sessionFactory);

        limparObjetoEvento();
        new EventService(sessionFactory).truncateTable();
        new RegionalService(sessionFactory).truncateTable();
        new CategoryService(sessionFactory).truncateTable();
        new InstituteService(sessionFactory).truncateTable();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);

        new InstituteService(sessionFactory).truncateTable();
    }

    @Test
    public void testRegisterSuccessfully() {
        Institute institute = new Institute();
        institute.setName("Creating Institute");
        Assert.assertTrue(instituteServiceUnderTest.save(institute));
    }

    @Test
    public void testDontRegister() {
        Assert.assertFalse(instituteServiceUnderTest.save(null));
    }

    @Test(expected = NullPointerException.class)
    public void testEdit() {
        //setup
        Institute institute = new Institute();
        institute.setId(1);
        institute.setName("Institute under test");

        //run
        instituteServiceUnderTest.editName(1, "New name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidCode() {
        //setup
        Institute institute = new Institute();
        institute.setId(1);

        //run
        instituteServiceUnderTest.remove(409);
    }

    @Test
    public void testRemove() {
        //setup
        generateRandomInstitute();
        List<Institute> institutes = instituteServiceUnderTest.getRecords();
        long id = institutes.get(0).getId();

        //run
        instituteServiceUnderTest.remove((int) id);

        assertEquals("", outContent.toString());
    }

    @Test
    public void testListByIdReturnsNoOne() {
        Institute regional = instituteServiceUnderTest.getById(409);

        assertNull(regional);
    }

    @Test
    public void testListByDescriptionReturnsEmptyList() {
        List<Institute> institutes = instituteServiceUnderTest.getRecordsByDescription("409");

        assertEquals(Collections.emptyList(), institutes);
    }

    private void generateRandomInstitute() {
        Institute institute = new Institute();
        institute.setName("Creating to test list");
        instituteServiceUnderTest.save(institute);
    }

    private void limparObjetoEvento() {
        EventService eventService = new EventService(sessionFactory);
        List<Event> events = eventService.getRecords();

        events.forEach(eventService::clearAttributes);
    }


}
