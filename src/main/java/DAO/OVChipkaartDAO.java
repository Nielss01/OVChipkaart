package DAO;

import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public void save(OVChipkaart ovChipkaart) ;
    public void update(OVChipkaart ovChipkaart) ;
    public void delete(OVChipkaart ovChipkaart) ;
    public List<OVChipkaart> findAll();

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) ;
    public OVChipkaart findOvChipkaartByID(Reiziger reiziger, int kaartnummer);

}
