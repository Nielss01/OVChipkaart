package Practium_2;

import Practium_2.DBFunction.ReizigerDAOPsql;
import Practium_2.domein.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;
    public static void main(String [] args) throws SQLException {
        getConnection();
        testReizigerDOA();
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

    private static void testReizigerDOA() throws SQLException {
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

        rdao.update(sietske, 10);
        System.out.println(sietske);
        rdao.findAll();
        System.out.println("Sietske's id is gewijzigd naar: " + sietske.getId());

        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Na de delete zijn er nog " + reizigers.size() + " over");



    }


}
