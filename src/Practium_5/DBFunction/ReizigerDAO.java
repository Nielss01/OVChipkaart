package Practium_5.DBFunction;

import Practium_5.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger, int id);
    public boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbDatum(String datum);
    public List<Reiziger> findAll();
}
