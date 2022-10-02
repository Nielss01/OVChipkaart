package Practium_5.DBFunction;

import Practium_5.Factory;
import Practium_5.FactoryConnection;
import Practium_5.domein.OVChipkaart;
import Practium_5.domein.Product;
import Practium_5.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    Factory factory;

    public OVChipkaartDAOPsql(Factory factory) {
        this.factory = factory;
    }

    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        String insertQuery = "INSERT INTO ov_chipkaart" +
                "(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES" +
                "(?,?,?,?,?);";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(insertQuery);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        preparedStatement.setDate(2, ovChipkaart.getGeldig_tot());
        preparedStatement.setInt(3, ovChipkaart.getKlasse());
        preparedStatement.setDouble(4, ovChipkaart.getSaldo());
        preparedStatement.setInt(5, ovChipkaart.getReiziger().getId());
        preparedStatement.executeUpdate();
        if(ovChipkaart.getAllProduct().size()!=0){
            for(Product product : ovChipkaart.getAllProduct()) {
                factory.getProductDAO().save(product);
                String insertQuery2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?,?)";
                PreparedStatement preparedStatement2 = FactoryConnection.getConnection().prepareStatement(insertQuery2);
                preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement2.setInt(2, product.getProduct_nummer());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }
        }
        preparedStatement.close();
        return true;
    }

    public boolean update(OVChipkaart ovChipkaart, double saldo){
        try{
            String updateQuery = "UPDATE ov_chipkaart "
                    + "SET saldo = ? "
                    + "WHERE kaart_nummer=?";
            PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(updateQuery);
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
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        String query = "DELETE from ov_chipkaart_product WHERE kaart_nummer=?";
        PreparedStatement preparedStatement2 = FactoryConnection.getConnection().prepareStatement(query);
        preparedStatement2.setInt(1,ovChipkaart.getKaart_nummer());
        preparedStatement2.executeUpdate();
        preparedStatement2.close();

        String deleteQuery = "DELETE from ov_chipkaart WHERE kaart_nummer=?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(deleteQuery);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    }
    public List<OVChipkaart> findAll() throws SQLException {
        ResultSet set;
        List<OVChipkaart> allOVChipkaarten = new ArrayList<>();
        String findQuery = "SELECT * FROM ov_chipkaart";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(findQuery);
        set = preparedStatement.executeQuery();
        while(set.next()) {
            int kaart_nummer  = set.getInt(1);
            Date geldig_tot = set.getDate(2);
            int klasse = set.getInt(3);
            double saldo = set.getDouble(4);
            allOVChipkaarten.add(new OVChipkaart(kaart_nummer,geldig_tot,klasse,saldo));
        }
        preparedStatement.close();
        return allOVChipkaarten;
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        ResultSet set;
        List<OVChipkaart> mijnOVChipkaarten = new ArrayList<>();
        String findQuery = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(findQuery);
        preparedStatement.setInt(1, reiziger.getId());
        set = preparedStatement.executeQuery();
        while (set.next()) {
            int kaart_nummer = set.getInt(1);
            Date geldig_tot = set.getDate(2);
            int klasse = set.getInt(3);
            double saldo = set.getDouble(4);
           OVChipkaart ovChipkaart= new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo);
           mijnOVChipkaarten.add(ovChipkaart);
           reiziger.getMijnOVChipkaarten().add(ovChipkaart);
        }
        return mijnOVChipkaarten;
    }

    public OVChipkaart findOvChipkaartByID(Reiziger reiziger, int kaartnummer, int product_nummer) throws SQLException {
        ResultSet set;
        OVChipkaart ovChipkaart = null;
        String findQuery = "select * from ov_chipkaart where kaart_nummer=? AND reiziger_id=?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(findQuery);
        preparedStatement.setInt(1, kaartnummer);
        preparedStatement.setInt(2,reiziger.getId());
        set = preparedStatement.executeQuery();
        while(set.next()){
            int kaart_nummer = set.getInt(1);
            Date geldig_tot = set.getDate(2);
            int klasse = set.getInt(3);
            double saldo = set.getDouble(4);
            ovChipkaart = new OVChipkaart(kaart_nummer,geldig_tot,klasse,saldo);
            ovChipkaart.setReiziger(reiziger);
        }
        assert ovChipkaart != null;
        factory.getProductDAO().findByOVChipkaart(ovChipkaart);
        return ovChipkaart;
    }




}
