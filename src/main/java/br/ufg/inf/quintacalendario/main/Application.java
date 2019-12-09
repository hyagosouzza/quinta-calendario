package br.ufg.inf.quintacalendario.main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.HomeViewConsole;

import java.util.Objects;

/**
 * Singleton.
 * Applications's main class. Responsible for starting the program's execution.
 */
public class Application {

    private static Application applicationInstance;
    private SessionFactory sessionFactory;

    /**
     * Class's default constructor. Initializes the session factory.
     */
    private Application() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Get the instance of the application.
     * @return Application Instance
     */
    public static synchronized Application getInstance() {
        if (Objects.isNull(applicationInstance)) {
            applicationInstance = new Application();
        }

        return applicationInstance;
    }

    /**
     * Get the Hibernate's SessionFactory
     * @return a SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Start the application's logic.
     */
    private void run() {
        HomeView startScreen = new HomeViewConsole(System.out);
        startScreen.displayHeader();
        startScreen.displayOptions();
    }

    /**
     * Application's entry point. Java default.
     * @param args Command line program arguments
     */
    public static void main(String[] args) {
        Application.getInstance().run();
    }

}
