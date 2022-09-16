package Practium_4.DBFunction;

import Practium_4.domein.Adres;
import Practium_4.domein.OVChipkaart;
import Practium_4.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart) throws SQLException;
    public boolean update(OVChipkaart ovChipkaart, double saldo);
    public boolean delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findAll();

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
}
