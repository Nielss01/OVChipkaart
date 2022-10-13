package Factories;

import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
import DAO.ReizigerDAO;
import HibernateDAO.AdresDAOHibernate;
import HibernateDAO.OVChipkaartDAOHibernate;
import HibernateDAO.ProductDAOHibernate;
import HibernateDAO.ReizigerDAOHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.ls.LSOutput;

public class DAOFactory {
    private ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(sessionFactory.getSession());
    private OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(sessionFactory.getSession(), this);
    private AdresDAOHibernate adao = new AdresDAOHibernate(sessionFactory.getSession());
    private ProductDAOHibernate pdao = new ProductDAOHibernate(sessionFactory.getSession());

    public static DAOFactory newInstance() {
        return new DAOFactory();
    }
    public ReizigerDAO getReizigerDAO() {
        return rdao;
    }

    public AdresDAO getAdresDAO(){
        return  adao;
    }
    public OVChipkaartDAO getOvChipkaartDAO(){
        return odao;
    }
    public ProductDAO getProductDAO(){
        return pdao;
    }

}
