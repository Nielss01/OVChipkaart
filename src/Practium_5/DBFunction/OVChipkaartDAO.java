package Practium_5.DBFunction;

import Practium_5.domein.OVChipkaart;
import Practium_5.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart) throws SQLException;
    public boolean update(OVChipkaart ovChipkaart, double saldo);
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public OVChipkaart findOvChipkaartByID(Reiziger reiziger, int kaartnummer, int product_nummer) throws SQLException;

}
