package DAO;

import domein.OVChipkaart;
import domein.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public void save(Product product) ;
    public void update(Product product) ;
    public void delete(Product product) ;
    public List findByOVChipkaart(OVChipkaart ovChipkaart);
}
