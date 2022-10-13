package HibernateDAO;

import DAO.ReizigerDAO;
import Factories.DAOFactory;
import Factories.sessionFactory;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }


    public void save(Reiziger reiziger)  {
        Transaction tx = session.beginTransaction();
        try {
            session.save(reiziger);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    public void update(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(reiziger);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }

    }


    public void delete(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(reiziger);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    public Reiziger findById(int id) {
        Transaction tx = session.beginTransaction();
        try {
            Reiziger reiziger = session.get(Reiziger.class, id);
            tx.commit();
            return reiziger;
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    public List<Reiziger> findByGbDatum(String datum) {
        Transaction tx = session.beginTransaction();
        try {
            String hql = "from Reiziger r where r.geboortedatum=:date";
            Query query = session.createQuery(hql);
            query.setParameter("date", Date.valueOf(datum));
            tx.commit();
            return query.list();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    public List<Reiziger> findAll() {
        Transaction tx = session.beginTransaction();
        try{
        List<Reiziger> reizigers = session.createQuery("FROM Reiziger", Reiziger.class).getResultList();
        tx.commit();
        return reizigers;
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
