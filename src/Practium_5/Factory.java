package Practium_5;

import Practium_5.DBFunction.*;


public class Factory {
    private  ReizigerDAOPsql rdao = new ReizigerDAOPsql(this);
    private  OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(this);
    private  AdresDAOPsql adao = new AdresDAOPsql(this);
    private ProductDAOPsql pdao = new ProductDAOPsql(this);

    public static Factory newInstance() {
        return new Factory();
    }
    public  ReizigerDAO getReizigerDAO() {
        return rdao;
    }

    public AdresDAO getAdresDAO(){
        return  adao;
    }
    public OVChipkaartDAO getOvChipkaartDAO(){
        return odao;
    }
    public ProductDAO getProductDAO(){
        return pdao;
    }





}
