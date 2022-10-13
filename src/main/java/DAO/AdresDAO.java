package DAO;
import domein.Adres;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    public void save(Adres adres) ;
    public void update(Adres adres);
    public void delete(Adres adres);
    public List findByReiziger(Reiziger reiziger) throws SQLException;
    public List<Adres> findAll();

}
