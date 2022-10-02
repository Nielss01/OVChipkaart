package Practium_5;

import Practium_5.DBFunction.AdresDAOPsql;
import Practium_5.DBFunction.ProductDAOPsql;
import Practium_5.DBFunction.ReizigerDAOPsql;
import Practium_5.DBFunction.OVChipkaartDAOPsql;
import Practium_5.domein.Adres;
import Practium_5.domein.OVChipkaart;
import Practium_5.domein.Product;
import Practium_5.domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private final Factory factory = Factory.newInstance();
    Random random = new Random();
    List<Reiziger> reizigers = factory.getReizigerDAO().findAll();
    public static void main(String[] args) throws SQLException {
        FactoryConnection.getConnection();
        Main main = new Main();
        main.testReizigerDAO();
        main.testAdresDAO();
        main.testOVChipkaartDAO();
        main.testProductDAO();
        FactoryConnection.closeConnection();
    }

    public void testReizigerDAO() throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger jeroen = new Reiziger(77, "J", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        if(factory.getReizigerDAO().findById(jeroen.getId()) == null){
            factory.getReizigerDAO().save(jeroen);
        }
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("Haal alle gegevens op van reizigers id 5?");
        System.out.println(factory.getReizigerDAO().findById(5) + "\n");

        System.out.println("Haal alle gegevens van mensen die goboren zijn op: 2002-12-03");
        System.out.println(factory.getReizigerDAO().findByGbDatum("2002-12-03") + "\n");

        System.out.println();
        jeroen.setId(random.nextInt(200-100) + 100);
        System.out.println("Sietske's id is gewijzigd naar: " + jeroen.getId());


        factory.getReizigerDAO().delete(jeroen);
        System.out.println("Na de delete zijn er nog " + reizigers.size() + " over");
    }

    private void testAdresDAO() throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger chloe = new Reiziger(99, "C", "", "Boers", java.sql.Date.valueOf(gbdatum));
        if(factory.getReizigerDAO().findById(chloe.getId()) == null){
            factory.getReizigerDAO().save(chloe);
        }
        chloe.createNewAdres(80, "2461 RV", "10", "G. van Dijkstraat", "Langeraar");
        System.out.println(chloe);
        System.out.println();
        chloe.setNewAdres(80, "1010 RV", "10", "Langeraarse-weg", "Langeraar");
        System.out.println(chloe);
        System.out.println("Chloe adres infomatie");
        System.out.println(factory.getAdresDAO().findByReiziger(chloe));
        System.out.println(chloe);
        System.out.println("\n---------- Test Alle Informatie -------------");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        factory.getReizigerDAO().delete(chloe);
    }

    private void testOVChipkaartDAO() throws SQLException {
        System.out.println("\n---------- Test OvchipkaartDAO -------------");
        LocalDate gbd = LocalDate.of(2003, 3, 4);
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger oot = new Reiziger(404, "O", "van", "Bommel", Date.valueOf(gbd));
        if(factory.getReizigerDAO().findById(oot.getId()) == null){
            factory.getReizigerDAO().save(oot);
        }
        oot.createNewOvChipkaart(90, Date.valueOf(gbdatum), 2, 8);
        System.out.println(oot);
        oot.updateOvChipkaart(90,10);
        System.out.println();
        System.out.println(oot);
        System.out.println("\n---------- Test Alle Informatie -------------");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        oot.deleteOvChipkaart(90);
        System.out.println();
        System.out.println(oot);

    }

    private void testProductDAO() throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");
        LocalDate gbd = LocalDate.of(2003, 3, 4);
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger robin = new Reiziger(410, "R", "van", "Bommel", Date.valueOf(gbd));
        if(factory.getReizigerDAO().findById(robin.getId()) == null){
            factory.getReizigerDAO().save(robin);
        }
        robin.createNewOVChipkaartWithProduct(101, Date.valueOf(gbdatum), 2, 100,7,"test", "dit is een test", 0.00);
        OVChipkaart ovChipkaart =factory.getOvChipkaartDAO().findOvChipkaartByID(robin,101, 7);
        System.out.println(ovChipkaart);
        System.out.println();
        ovChipkaart.updateProduct(7,10.00,"actief");
        System.out.println(ovChipkaart);

        ovChipkaart.deleteProduct(7);

       Product product = new Product(8,"test","dit is een beschijving", 10.00);
       product.addOVChipkaartAndSave(ovChipkaart);
       product.save();
        System.out.println(robin);
        System.out.println();

       product.deleteOvChipkaartP(ovChipkaart);
       product.deleteProduct();
       robin.deleteOvChipkaart(101);


    }


}
