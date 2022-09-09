package Practium_3;

import Practium_2.DBFunction.ReizigerDAOPsql;
import Practium_2.domein.Reiziger;
import Practium_3.DBFunction.AdresDAOPsql;
import Practium_3.DBFunction.ReizigerDAO;
import Practium_3.domein.Adres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;
    public static void main(String [] args) throws SQLException {
        getConnection();
        testReizigerDAO();
        testAdresDAO();
        closeConnection();


    }
    private static void getConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Zu18Inig";
            connection = DriverManager.getConnection(url);
        }catch(SQLException | ClassNotFoundException sqlE){
            System.err.println("[SQLExpection] Couldnt connect with database" + sqlE.getMessage());
        }
    }
    private static void closeConnection(){
        try {
            connection.close();
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Couldnt close the database" + sqlE.getMessage());
        }
    }
    private static void testReizigerDAO() throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        LocalDate gbdatum = LocalDate.of(1981,3,14);
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("Haal alle gegevens op van reizigers id 5?");
        System.out.println(rdao.findById(5)+ "\n");

        System.out.println("Haal alle gegevens van mensen die goboren zijn op: 2002-12-03");
        System.out.println(rdao.findByGbDatum("2002-12-03")+ "\n");

//        rdao.update(sietske, 10);
        System.out.println(sietske);
        rdao.findAll();
        System.out.println("Sietske's id is gewijzigd naar: " + sietske.getId());

        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Na de delete zijn er nog " + reizigers.size() + " over");
    }

    private static void testAdresDAO(){
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        Practium_3.DBFunction.ReizigerDAOPsql rdao = new Practium_3.DBFunction.ReizigerDAOPsql(connection);
        System.out.println("\n---------- Test AdresDAO -------------");
        LocalDate gbdatum = LocalDate.of(1981,3,14);
        Practium_3.domein.Reiziger sietske = new Practium_3.domein.Reiziger(10, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));

        Adres adres1 = new Adres(33,"2461 RV" , "10","G. van Dijkstraat","Langeraar", 10);
//        adao.save(adres1);
        System.out.println("Adres is opgeslagen");
        System.out.println();
//        Adres newadres = new Adres(33,"2461 RV" , "12","G. van Dijkstraat","Langeraar", 10);
//        adao.update(newadres);
//        System.out.println("Adres is gewijzigd");
//        adao.delete(newadres);
//        System.out.println("Adres is gedelete");
//        System.out.println("Sietske adres infomatie");
//        System.out.println(adao.findByReiziger(sietske));
        LocalDate geboor = LocalDate.of(2003,10,29);
        Practium_3.domein.Reiziger niels = new Practium_3.domein.Reiziger(100, "M", "", "Zevenhoven",java.sql.Date.valueOf(geboor));
        Adres nAdres = new Adres(18,"0987IO","20", "De Kamp","Langeraar", niels.getId());
        niels.setAdres(nAdres);
        rdao.setAdresDAO(adao);
        rdao.save(niels);
        System.out.println("\n---------- Test Alle Informatie -------------");
        List<Practium_3.domein.Reiziger> reizigers = rdao.findAll();
        for(Practium_3.domein.Reiziger r : reizigers){
            System.out.println(r);
        }
        System.out.println();



    }

}
