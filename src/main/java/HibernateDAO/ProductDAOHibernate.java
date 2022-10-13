package HibernateDAO;

import DAO.ProductDAO;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public void save(Product product)  {
        Transaction tx = session.beginTransaction();
        try {
            session.save(product);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void update(Product product) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(product);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(Product product)  {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(product);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List findByOVChipkaart(OVChipkaart ovChipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            String hql = ("from Product where allOvChipkaart = :id");
            Query query = session.createQuery(hql);
            query.setParameter("id", ovChipkaart.getKaart_nummer());
            tx.commit();
            return query.getResultList();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


}
