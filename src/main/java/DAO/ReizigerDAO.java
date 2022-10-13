package DAO;

import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public void save(Reiziger reiziger);
    public void update(Reiziger reiziger);
    public void delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbDatum(String datum);
    public List<Reiziger> findAll();
}
