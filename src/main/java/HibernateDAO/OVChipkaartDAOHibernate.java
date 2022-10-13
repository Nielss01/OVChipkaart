package HibernateDAO;

import DAO.OVChipkaartDAO;
import Factories.DAOFactory;
import domein.OVChipkaart;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    Session session;
    DAOFactory daoFactory;

    public OVChipkaartDAOHibernate(Session session, DAOFactory daoFactory) {
        this.session = session;
        this.daoFactory = daoFactory;
    }

    public void save(OVChipkaart ovChipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(ovChipkaart);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }

    }

    public void update(OVChipkaart ovChipkaart)  {
        Transaction tx = session.beginTransaction();
        try {
            session.update(ovChipkaart);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(ovChipkaart);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List<OVChipkaart> findAll()  {
        Transaction tx = session.beginTransaction();
        try {
            List<OVChipkaart> ovChipkaartList = session.createQuery("FROM OVChipkaart ", OVChipkaart.class).getResultList();
            tx.commit();
            return ovChipkaartList;
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            String hql = ("from OVChipkaart where reiziger = :id");
            Query query = session.createQuery(hql);
            query.setParameter("id", reiziger.getReiziger_id());
            tx.commit();
            return query.getResultList();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public OVChipkaart findOvChipkaartByID(Reiziger reiziger, int kaartnummer) {
        Transaction tx = session.beginTransaction();
        try {
            OVChipkaart foundOv = session.get(OVChipkaart.class, kaartnummer);
            tx.commit();
            return foundOv;
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
