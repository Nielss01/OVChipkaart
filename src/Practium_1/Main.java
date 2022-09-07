package Practium_1;

import java.sql.*;
public class Main {

    public static void main(String [] args){
        Connection conn = null;
        Statement stmt = null;
        ResultSet set;
        try{
            //Loading the driver
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Zu18Inig";
            //Connection with database
            conn = DriverManager.getConnection(url);
            //Create a statement to execute the query
            stmt = conn.createStatement();
            String query = "SELECT * from reiziger";
            //Resultset with the results from the query
            set = stmt.executeQuery(query);
            System.out.println("Alle Reizigers: ");
            //Go through every colum
            while(set.next()){
                String reisnummer = set.getString(1);
                String voorletter = set.getString(2);
                String tussenvoegsel = set.getString(3);
                if(tussenvoegsel == null){
                    tussenvoegsel = "";
                }
                String achternaam = set.getString(4);
                Date geboortedatum = set.getDate(5);

                System.out.println("#" + reisnummer + ": " + voorletter + ". " + tussenvoegsel + " " + achternaam + " (" + geboortedatum + ")");
            }
            conn.close();
            stmt.close();
            set.close();
        }catch(SQLException | ClassNotFoundException sqle){
            System.err.println("[SQLExpection] Reizigers konden niet worden opgehaald " + sqle.getMessage());

        }
    }
}
