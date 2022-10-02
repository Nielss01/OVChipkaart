package Practium_5.domein;

import Practium_5.Factory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private final List<OVChipkaart> allOvChipkaart = new ArrayList<>();
    Factory factory = Factory.newInstance();

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }
    public void save() throws SQLException {
        factory.getProductDAO().save(this);
    }

    public void addOVChipkaartAndSave(OVChipkaart ovChipkaart) throws SQLException {
        allOvChipkaart.add(ovChipkaart);
//        for (int i = 0; i < factory.getOvChipkaartDAO().findAll().size(); i++) {
//            if (!factory.getOvChipkaartDAO().findAll().get(i).equals(ovChipkaart)) {
//                System.out.println("test");
//                factory.getOvChipkaartDAO().save(ovChipkaart);
//            }
//        }
    }

    public void deleteOvChipkaartP(OVChipkaart ovChipkaart) throws SQLException {
        allOvChipkaart.remove(ovChipkaart);
        factory.getOvChipkaartDAO().delete(ovChipkaart);
    }
    public void deleteProduct() throws SQLException {
        Product product = null;
        factory.getProductDAO().delete(this);
    }


    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart){
        allOvChipkaart.add(ovChipkaart);
    }
    public void removeOVChipkaart(OVChipkaart ovChipkaart){
        allOvChipkaart.remove(ovChipkaart);
    }
    public List<OVChipkaart> getAllOvChipkaart(){
        return allOvChipkaart;
    }

    @Override
    public String toString() {
        return
                product_nummer + " " +
                 naam + " "
                + beschrijving + " " +
                prijs ;

    }
}
