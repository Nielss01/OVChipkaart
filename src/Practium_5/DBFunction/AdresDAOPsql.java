package Practium_5.DBFunction;


import Practium_5.domein.Adres;
import Practium_5.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    Connection conn;
    ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres)  {
        try {
            String insertQuery = "INSERT INTO adres" +
                    "  (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES " +
                    " (?, ?, ?, ?, ?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, adres.getAdres_id());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6,adres.getReiziger_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Adres kan niet worden opgeslagen " + sqlE.getMessage());
            return false;
        }
    }

    public boolean update(Adres adres) {
        try{
            String updateQuery = "UPDATE adres "
                    + "SET postcode=?, huisnummer=?, straat=?, woonplaats=? "
                    + "WHERE reiziger_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setString(1, adres.getPostcode());
            preparedStatement.setString(2, adres.getHuisnummer());
            preparedStatement.setString(3, adres.getStraat());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getReiziger_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] Adres kan niet worden geupdate " + sqlE.getMessage());
            return false;
        }
    }
    public boolean delete(Adres adres) {
        try {
            String deleteQuery = "DELETE from adres WHERE adres_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, adres.getAdres_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException sqlE){
            System.err.println("[SQLExpection] Adres kan niet worden gedelete " + sqlE.getMessage());
            return false;
        }
    }
    public Adres findByReiziger(Reiziger reiziger) {
        try{
            ResultSet set;
            String findQuery = "SELECT * FROM adres WHERE reiziger_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            preparedStatement.setInt(1, reiziger.getId());
            set = preparedStatement.executeQuery();
            while(set.next()) {
                int adres_id= set.getInt(1);
                String postcode = set.getString(2);
                String huisnummer = set.getString(3);
                String straat = set.getString(4);
                String woonplaats = set.getString(5);
                int reiziger_id = reiziger.getId();
                return(new Adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id));
            }
            preparedStatement.close();
        }catch (SQLException sqlE){
            System.err.println("[SQLExpection] Adres kan niet worden gevonden" + sqlE.getMessage());
        }
        return null;


    }
    public List<Adres> findAll() {
        try{
            ResultSet set;
            List<Adres> alleAdressen = new ArrayList<>();
            String findQuery = "SELECT * FROM adres";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            set = preparedStatement.executeQuery();
            while(set.next()) {
                int adres_id= set.getInt(1);
                String postcode = set.getString(2);
                String huisnummer = set.getString(3);
                String straat = set.getString(4);
                String woonplaats = set.getString(5);
                int reiziger_id = set.getInt(6);

                alleAdressen.add(new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id));
            }
            preparedStatement.close();
            return alleAdressen;
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] Adressen niet gevonden" + sqlE.getMessage());
        }
        return null;
    }
}
