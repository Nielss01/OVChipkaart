package HibernateDAO;

import DAO.AdresDAO;
import Factories.sessionFactory;
import domein.Adres;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public void save(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(adres);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void update(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(adres);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(adres);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List findByReiziger(Reiziger reiziger)  {
        Transaction tx = session.beginTransaction();
        try {
            String hql = "from Adres A where A.reiziger.reiziger_id =:reiziger_id";
            Query query = session.createQuery(hql);
            query.setParameter("reiziger_id", reiziger.getReiziger_id());
            tx.commit();
            return query.list();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Adres> findAll() {
        Transaction tx = session.beginTransaction();
        try {
            List<Adres> AdresList = session.createQuery("FROM Adres ", Adres.class).getResultList();
            tx.commit();
            return AdresList;
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
