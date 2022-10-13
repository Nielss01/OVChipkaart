package Factories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class sessionFactory {
    private static SessionFactory factory;
    private static Session session = null;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession() throws HibernateException {
        if(session== null){
            session = factory.openSession();
        }
        return session;
    }


}



