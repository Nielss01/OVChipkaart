package Practium_5.DBFunction;

import Practium_5.domein.Product;

import java.sql.SQLException;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product, int id);
    public boolean delete(Product product);
}
