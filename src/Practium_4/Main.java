package Practium_4;

import Practium_4.DBFunction.*;
import Practium_4.domein.Adres;
import Practium_4.domein.OVChipkaart;
import Practium_4.domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Connection connection;
    public static void main(String [] args) throws SQLException {
        getConnection();
        testReizigerDAO();
        testAdresDAO();
        testOVChipkaartDAO();
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


        System.out.println(sietske);
        rdao.findAll();
        System.out.println("Sietske's id is gewijzigd naar: " + sietske.getId());

        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Na de delete zijn er nog " + reizigers.size() + " over");
    }

    private static void testAdresDAO() {
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        System.out.println("\n---------- Test AdresDAO -------------");
        LocalDate gbdatum = LocalDate.of(1981, 3, 14);
        Reiziger sietske = new Reiziger(10, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));

        Adres adres1 = new Adres(33, "2461 RV", "10", "G. van Dijkstraat", "Langeraar", 10);
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
        LocalDate geboor = LocalDate.of(2003, 10, 29);
        Reiziger niels = new Reiziger(100, "M", "", "Zevenhoven", java.sql.Date.valueOf(geboor));
        Adres nAdres = new Adres(18, "0987IO", "20", "De Kamp", "Langeraar", niels.getId());
        niels.setAdres(nAdres);
        rdao.setAdresDAO(adao);
//        rdao.save(niels);
        System.out.println("\n---------- Test Alle Informatie -------------");
        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
    }

    private static void testOVChipkaartDAO() throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        LocalDate geldig_tot = LocalDate.of(2022, 9, 15);

        List<OVChipkaart> ovChipkaartList = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for(OVChipkaart ovChipkaart: ovChipkaartList){
            System.out.println(ovChipkaart);
        }
        OVChipkaart ovChipkaart = new OVChipkaart(56834,Date.valueOf(geldig_tot), 1, 15.00,10);
        Reiziger reiziger = rdao.findById(2);
        System.out.println(reiziger);
        System.out.println();
        odao.delete(ovChipkaart);
        System.out.println("Aantal OVChipkaarten voor de save: " + ovChipkaartList.size());
        odao.findAll();
        odao.save(ovChipkaart);
        ovChipkaartList = odao.findAll();
        System.out.println();
        System.out.println("Aantal OVChipkaarten na de save: " + ovChipkaartList.size());
        System.out.println();
        odao.update(ovChipkaart, 40);
        System.out.println("Update heeft het saldo aangepast naar 40 euro" );
        System.out.println();
        System.out.println();
        odao.delete(ovChipkaart);
        List<OVChipkaart> myOVChipkaarten = odao.findByReiziger(reiziger);
        for (OVChipkaart ov : myOVChipkaarten){
            System.out.println(ov);
        }
        LocalDate gbd = LocalDate.of(2003, 3, 4);
        LocalDate geldi = LocalDate.of(2023, 3, 4);
        Reiziger newReiziger = new Reiziger(88, "J", "van","Straalen",Date.valueOf(gbd));
        Adres newAdres = new Adres(21,"0977OI","30","RaadhuizeStraat", "Nieuwkoop", 88);
        List<OVChipkaart> newOVChipkaartlist = new ArrayList<>();

        newOVChipkaartlist.add(new OVChipkaart(98,Date.valueOf(geldi), 2, 50, 88 ));
        newReiziger.setAdres(newAdres);
        rdao.setAdresDAO(adao);
        rdao.setOdao(odao);
        newReiziger.setMijnOVChipkaarten(newOVChipkaartlist);
//        rdao.delete(newReiziger );
//        rdao.save(newReiziger);
        System.out.println("\n---------- Test Alle Informatie -------------");
        System.out.println();
        List<Reiziger> alleReizigers = rdao.findAll();
        for(Reiziger r : alleReizigers){
            System.out.println(r);
        }


    }
}
