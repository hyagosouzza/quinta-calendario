package br.ufg.inf.quintacalendario.service;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Regional;
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

public class RegionalServiceTest {

    private RegionalService regionalServiceUnderTest;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static SessionFactory sessionFactory;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        sessionFactory = Application.getInstance().getSessionFactory();
        regionalServiceUnderTest = new RegionalService(sessionFactory);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testRegisterSuccessfully() {
        Regional regional = new Regional();
        regional.setName("Creating Regional");

        Assert.assertTrue(regionalServiceUnderTest.save(regional));
    }

    @Test
    public void testDontRegister() {
        Assert.assertFalse(regionalServiceUnderTest.save(null));
    }

    @Test(expected = NullPointerException.class)
    public void testEdit() {
        //setup
        Regional regional = new Regional();
        regional.setId(1);
        regional.setName("Regional under test");

        //run
        regionalServiceUnderTest.edit(1, "New name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidCode() {
        //setup
        Regional regional = new Regional();
        regional.setId(1);

        //run
        regionalServiceUnderTest.remove(409);
    }

    @Test
    public void testListByIdReturnsNoOne() {
        Regional regional = regionalServiceUnderTest.getById(409);

        assertNull(regional);
    }

    @Test
    public void testListByDescriptionReturnsEmptyList() {
        List<Regional> regionals = regionalServiceUnderTest.listRecordsByDescription("409");

        assertEquals(Collections.emptyList(), regionals);
    }

}
