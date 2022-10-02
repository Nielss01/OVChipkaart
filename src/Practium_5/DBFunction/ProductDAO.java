package Practium_5.DBFunction;

import Practium_5.domein.OVChipkaart;
import Practium_5.domein.Product;

import java.sql.SQLException;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product, double prijs, String status) throws SQLException;
    public boolean delete(Product product) throws SQLException;
    public Product findByOVChipkaart(OVChipkaart ovChipkaart)throws SQLException;
}
