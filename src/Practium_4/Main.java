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
