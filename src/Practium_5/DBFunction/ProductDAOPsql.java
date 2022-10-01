package Practium_5.DBFunction;

import Practium_5.Factory;
import Practium_5.FactoryConnection;
import Practium_5.domein.OVChipkaart;
import Practium_5.domein.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    Factory factory;

    public ProductDAOPsql(Factory factory) {
        this.factory  = factory;
    }

    public boolean save(Product product) throws SQLException{
        String insertQuery= "INSERT INTO product"+
                "(product_nummer, naam, beschrijving, prijs) VALUES" +
                "(?,?,?,?);";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(insertQuery);
        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.setString(2, product.getNaam());
        preparedStatement.setString(3, product.getBeschrijving());
        preparedStatement.setDouble(4, product.getPrijs());
        preparedStatement.executeUpdate();
        for(OVChipkaart ov : product.getAllOvChipkaart()){
            String query = "INSERT INTO ov_chipkaart_product " + "(product_nummer, kaart_nummer) VALUES " +
                    "(?, ?);";
            preparedStatement = FactoryConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setInt(2, ov.getKaart_nummer());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        return true;
    }
    public boolean update(Product product, int prijs,String status) throws SQLException {
        String updateQuery = "UPDATE product "
                + "SET prijs = ? "
                + "WHERE product_nummer=?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(updateQuery);
        preparedStatement.setDouble(1, prijs);
        preparedStatement.setInt(2, product.getProduct_nummer());
        preparedStatement.executeUpdate();
        for(OVChipkaart ov : product.getAllOvChipkaart()){
            String query = "UPDATE ov_chipkaart_product"
                    + "SET status=?"
                    + "WHERE kaart_nummer=?";
            preparedStatement = FactoryConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2,ov.getKaart_nummer());
        }
        preparedStatement.close();
        return true;
    }
    public boolean delete(Product product) throws SQLException {
        String deleteQuery = "DELETE from product WHERE product_nummer=?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(deleteQuery);
        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.executeUpdate();
        for(OVChipkaart ov : product.getAllOvChipkaart()){
            String query = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer=?";
            preparedStatement = FactoryConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ov.getKaart_nummer());
        }
        preparedStatement.close();
        return true;
    }

    public Product findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        ResultSet set;
        String findQuery = "select product.product_nummer,product.naam, product.beschrijving, product.prijs from ov_chipkaart_product" +
                "inner join product ON product.product_nummer = ov_chipkaart_product.product_nummer" +
                "where ov_chipkaart_product.kaart_nummer =?";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(findQuery);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        set = preparedStatement.executeQuery();
        while(set.next()) {
            int product_nummer = set.getInt(1);
            String naam = set.getString(2);
            String beschrijving = set.getString(3);
            double prijs = set.getDouble(4);
            return new Product(product_nummer,naam,beschrijving,prijs);
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        ResultSet set;
        List<Product> allProduct = new ArrayList<>();
        String findQuery = "select * from product";
        PreparedStatement preparedStatement = FactoryConnection.getConnection().prepareStatement(findQuery);
        set = preparedStatement.executeQuery();
        while(set.next()){
            int product_nummer = set.getInt(1);
            String naam = set.getString(2);
            String beschrijving = set.getString(3);
            double prijs = set.getDouble(4);
            allProduct.add(new Product(product_nummer,naam,beschrijving,prijs));
        }
        String secFindQuery = "select ov_chipkaart.kaart_nummer, ov_chipkaart.geldig_tot," +
                "ov_chipkaart.klasse, ov_chipkaart.saldo, ov_chipkaart.reiziger_id from ov_chipkaart_product" +
                "inner join ov_chipkaart ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer" +
                "where ov_chipkaart_product.product_nummer =?";
        for(Product product : allProduct){
            PreparedStatement preparedStatement1 = FactoryConnection.getConnection().prepareStatement(secFindQuery);
            preparedStatement1.setInt(1, product.getProduct_nummer());
            set = preparedStatement.executeQuery();
            while(set.next()){
                int kaart_nummer = set.getInt(1);
                Date geldig_tot = set.getDate(2);
                int klasse = set.getInt(3);
                double saldo = set.getDouble(4);
                product.addOVChipkaart(new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo));
            }
        }
        return allProduct;
    }
}
