package Practium_2.DBFunction;

import Practium_2.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }
    public boolean save(Reiziger reiziger) {
        try {
            String insertQuery = "INSERT INTO reiziger" +
                    "  (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES " +
                    " (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Reiziger kan niet worden opgeslagen " + sqlE.getMessage());
            return false;
        }
    }
    public boolean update(Reiziger reiziger, int id){
        try{
            String updateQuery = "UPDATE reiziger "
                    + "SET reiziger_id = ? "
                    + "WHERE voorletters=? AND achternaam = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3,reiziger.getAchternaam());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] Reiziger kan niet worden geupdate " + sqlE.getMessage());
            return false;
        }
    }
    public boolean delete(Reiziger reiziger){
        try {
            String deleteQuery = "DELETE from reiziger WHERE reiziger_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException sqlE){
            System.err.println("[SQLExpection] Reiziger kan niet worden gedelete " + sqlE.getMessage());
            return false;
        }
    }
    public Reiziger findById(int id){
        try {
            ResultSet set;
            String findQuery = "SELECT * FROM reiziger WHERE reiziger_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            preparedStatement.setInt(1, id);
            set = preparedStatement.executeQuery();
            while(set.next()){
                int newid = set.getInt(1);
                String voorletter = set.getString(2);
                String tussenvoegsel = set.getString(3);
                if(tussenvoegsel == null){
                    tussenvoegsel = "";
                }
                String achternaam = set.getString(4);
                Date geboortedatum = set.getDate(5);

                return new Reiziger(newid, voorletter, tussenvoegsel, achternaam,geboortedatum);
            }
            preparedStatement.close();
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Reiziger kan niet worden gevonden" + sqlE.getMessage());
        }
        return null;
    }
    public List<Reiziger> findByGbDatum(String datum){
        try{
            ResultSet set;
            List<Reiziger> allReizigers = new ArrayList<>();
            String findQuery = "SELECT * FROM reiziger WHERE geboortedatum=?";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            preparedStatement.setDate(1, java.sql.Date.valueOf(datum));
            set = preparedStatement.executeQuery();
            while(set.next()) {
                int newid = set.getInt(1);
                String voorletter = set.getString(2);
                String tussenvoegsel = set.getString(3);
                if(tussenvoegsel == null){
                    tussenvoegsel = "";
                }
                String achternaam = set.getString(4);
                Date geboortedatum = set.getDate(5);

                Reiziger reiziger = new Reiziger(newid, voorletter, tussenvoegsel, achternaam, geboortedatum);
                allReizigers.add(reiziger);
            }
            preparedStatement.close();
            return allReizigers;
        }catch (SQLException sqlE){
            System.err.println("[SQLExpection] Reiziger kan niet worden gevonden" + sqlE.getMessage());
        }
        return null;
    }
    public List<Reiziger> findAll(){
        try{
            ResultSet set;
            List<Reiziger> allReizigers = new ArrayList<>();
            String findQuery = "SELECT * FROM reiziger";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            set = preparedStatement.executeQuery();
            while(set.next()) {
                int newid = set.getInt(1);
                String voorletter = set.getString(2);
                String tussenvoegsel = set.getString(3);
                if(tussenvoegsel == null){
                    tussenvoegsel = "";
                }
                String achternaam = set.getString(4);
                Date geboortedatum = set.getDate(5);

                Reiziger reiziger = new Reiziger(newid, voorletter, tussenvoegsel, achternaam, geboortedatum);
                allReizigers.add(reiziger);
            }
            preparedStatement.close();
            return allReizigers;
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Reizigers niet gevonden" + sqlE.getMessage());
        }
        return null;
    }
}
