import Factories.DAOFactory;
import Factories.sessionFactory;
import domein.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private final DAOFactory daoFactory = DAOFactory.newInstance();
    List<Reiziger> reizigers = daoFactory.getReizigerDAO().findAll();


    public static void main(String[] args) throws SQLException {
        testFetchAll();
        Main main = new Main();
        main.testReiziger();
        main.testAdres();
        main.testOvChipkaart();
        main.testProduct();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {

            Metamodel metamodel = sessionFactory.getSession().getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = sessionFactory.getSession().createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
    }

    private void testReiziger() throws SQLException {
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger reiziger = new Reiziger(450,"D"," ", "Spaans", Date.valueOf(gbdatum));
        if(daoFactory.getReizigerDAO().findById(reiziger.getReiziger_id()) == null){
            daoFactory.getReizigerDAO().save(reiziger);
        }
        reiziger.setTussenvoegsel("van");
        System.out.println(reiziger);
        System.out.println("Haal alle gegevens van mensen die goboren zijn op: 2002-12-03");
        System.out.println(daoFactory.getReizigerDAO().findByGbDatum("2002-12-03") + "\n");

        System.out.println("------------Alle Reizigers:------------");
        for(Reiziger reizig : reizigers){
            System.out.println(reizig);
        }
        daoFactory.getReizigerDAO().delete(reiziger);
    }
    public void testAdres() throws SQLException {
        System.out.println("--------------TestAdres--------------");
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger lars = new Reiziger(901, "Lars", "", "Pieterse", Date.valueOf(gbdatum));
        if(daoFactory.getReizigerDAO().findById(lars.getReiziger_id()) == null){
            daoFactory.getReizigerDAO().save(lars);
        }
        lars.createNewAdres(505L,"7689 TY", "8","Langeraarseweg", "Langeraar");
        System.out.println(lars);
        System.out.println();
        System.out.println("Lars zijn Adres info: " + daoFactory.getAdresDAO().findByReiziger(lars));
        System.out.println();
        lars.getAdres().setHuisnummer("10");
        System.out.println(lars);
        List<Adres> adresList = daoFactory.getAdresDAO().findAll();
        for(Adres adres : adresList){
            System.out.println(adres);
        }
        lars.deleteAdres();
    }
    
    
    


    public void testOvChipkaart() throws SQLException {
        System.out.println("--------------TestOvChipkaart--------------");
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        LocalDate geldig = LocalDate.of(2023,1,1);
        Reiziger corrie = new Reiziger(900, "C", "", "Pieterse", Date.valueOf(gbdatum));
        if(daoFactory.getReizigerDAO().findById(corrie.getReiziger_id()) == null){
            daoFactory.getReizigerDAO().save(corrie);
        }
        corrie.createNewOvChipkaart(600,Date.valueOf(geldig),2, 10.0);
        System.out.println(corrie);
        corrie.updateOvChipkaart(600, 50.0);
        System.out.println();
        System.out.println(corrie);
        List<OVChipkaart> ovChipkaartList = daoFactory.getOvChipkaartDAO().findAll();
        ///find all doet t nog niet helemaal
        System.out.println("\n---------- Test Alle Informatie -------------");
        for (OVChipkaart ovChipkaart : ovChipkaartList) {
            System.out.println(ovChipkaart);
        }
        corrie.deleteOvChipkaart(600);
    }
    public void testProduct() throws SQLException {
        System.out.println("--------------TestProduct--------------");
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        LocalDate geldig = LocalDate.of(2023,1,1);
        Reiziger mitch = new Reiziger(879,"M", " ", "Woerde", Date.valueOf(gbdatum));
        if(daoFactory.getReizigerDAO().findById(mitch.getReiziger_id()) == null){
            daoFactory.getReizigerDAO().save(mitch);
        }
        mitch.createNewOVChipkaartWithProduct(109, Date.valueOf(geldig), 2,10.0, 465,"hibernate","test hibernate", 90.0);
        OVChipkaart ovChipkaart = daoFactory.getOvChipkaartDAO().findOvChipkaartByID(mitch,109);
        System.out.println(ovChipkaart);
        mitch.updateProductforReiziger(109,465, 80.0);
        System.out.println();
        System.out.println(ovChipkaart);
        ovChipkaart.deleteProduct(465);

        Product product = new Product(101,"test","dit is een beschijving", 10.00);
        product.addOVChipkaart(ovChipkaart);
        product.save();
        System.out.println();
        System.out.println(ovChipkaart);
        System.out.println(mitch);
        System.out.println();
        product.deleteOvChipkaartP(ovChipkaart);
        product.deleteProduct();
        mitch.deleteOvChipkaart(109);
        System.out.println(mitch);
        sessionFactory.getSession().close();
    }


}