package Practium_4.DBFunction;

import Practium_4.domein.Adres;
import Practium_4.domein.OVChipkaart;
import Practium_4.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(OVChipkaart ovChipkaart){
        try{
            String insertQuery= "INSERT INTO ov_chipkaart"+
                    "(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES" +
                    "(?,?,?,?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement.setDate(2, ovChipkaart.getGeldig_tot());
            preparedStatement.setInt(3, ovChipkaart.getKlasse());
            preparedStatement.setDouble(4, ovChipkaart.getSaldo());
            preparedStatement.setInt(5, ovChipkaart.getReiziger_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] OVChipkaart kan niet worden opgeslagen " + sqlE.getMessage());
            return false;
        }
    }

    public boolean update(OVChipkaart ovChipkaart, double saldo){
        try{
            String updateQuery = "UPDATE ov_chipkaart "
                    + "SET saldo = ? "
                    + "WHERE kaart_nummer=?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, saldo);
            preparedStatement.setInt(2, ovChipkaart.getKaart_nummer());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] OVChipkaart kan niet worden geupdate " + sqlE.getMessage());
            return false;
        }
    }
    public boolean delete(OVChipkaart ovChipkaart){
        try {
            String deleteQuery = "DELETE from ov_chipkaart WHERE kaart_nummer=?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException sqlE){
            System.err.println("[SQLExpection] OVChipkaart kan niet worden gedelete " + sqlE.getMessage());
            return false;
        }
    }
    public List<OVChipkaart> findAll(){
        try{
            ResultSet set;
            List<OVChipkaart> allOVChipkaarten = new ArrayList<>();
            String findQuery = "SELECT * FROM ov_chipkaart";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            set = preparedStatement.executeQuery();
            while(set.next()) {
                int kaart_nummer  = set.getInt(1);
                java.sql.Date geldig_tot = set.getDate(2);
                int klasse = set.getInt(3);
                double saldo = set.getDouble(4);
                int reiziger_id = set.getInt(5);
                allOVChipkaarten.add(new OVChipkaart(kaart_nummer,geldig_tot,klasse,saldo,reiziger_id));
            }
            preparedStatement.close();
            return allOVChipkaarten;
        }catch(SQLException sqlE){
            System.err.println("[SQLExpection] OVChipkaarten kunnen niet gevonden worden" + sqlE.getMessage());
        }
        return null;
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        try{
            ResultSet set;
            List<OVChipkaart> mijnOVChipkaarten = new ArrayList<>();
            String findQuery = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(findQuery);
            preparedStatement.setInt(1,reiziger.getId());
            set = preparedStatement.executeQuery();
            while(set.next()){
                int kaart_nummer = set.getInt(1);
                Date geldig_tot = set.getDate(2);
                int klasse = set.getInt(3);
                double saldo = set.getDouble(4);
                mijnOVChipkaarten.add(new OVChipkaart(kaart_nummer, geldig_tot,klasse,saldo,reiziger.getId()));
            }
            return mijnOVChipkaarten;
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] OVChipkaarten kunnen niet worden ingeladen " + sqlE.getMessage());
        }
        return null;
    }


}
