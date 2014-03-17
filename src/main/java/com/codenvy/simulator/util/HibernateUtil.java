package com.codenvy.simulator.util;

    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.Configuration;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            SessionFactory sessionFactory = new Configuration().configure(
                    "hibernate.cfg.xml")
                    .buildSessionFactory();

            return sessionFactory;

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();

        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}

