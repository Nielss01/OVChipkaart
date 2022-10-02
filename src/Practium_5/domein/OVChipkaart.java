package Practium_5.domein;

import Practium_5.Factory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;
    private final List<Product> allProduct = new ArrayList<>();
    Factory factory = Factory.newInstance();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

     public void removeProduct(Product product){
        allProduct.remove(product);
     }


    @Override
    public String toString() {
        return "OVChipkaart: " + "Kaartnummer: " +
                kaart_nummer + " "+ "| Geldig tot: " +
                geldig_tot +" "+ "| Klasse: "+
                klasse +" "+ "| Saldo: " +
                saldo +  " " +allProduct;
    }

    public List<Product> getAllProduct() {
        return allProduct;
    }
    public void addProduct(Product product){
        allProduct.add(product);
    }

    public void deleteProduct(int product_nummer) throws SQLException {
        for (int i = 0; i < allProduct.size(); i++) {
            if (allProduct.get(i).getProduct_nummer() == product_nummer) {
                factory.getProductDAO().delete(allProduct.get(i));
                allProduct.remove(allProduct.get(i));
            }
        }
    }

    public void updateProduct(int product_nummer, double prijs,String status) throws SQLException {
        for (int i = 0; i < allProduct.size(); i++) {
            if (allProduct.get(i).getProduct_nummer() == product_nummer) {
                factory.getProductDAO().update(allProduct.get(i), prijs,status);
                allProduct.get(i).setPrijs(prijs);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OVChipkaart)) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaart_nummer == that.kaart_nummer && klasse == that.klasse && Double.compare(that.saldo, saldo) == 0 && Objects.equals(geldig_tot, that.geldig_tot) && Objects.equals(reiziger, that.reiziger) && Objects.equals(allProduct, that.allProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kaart_nummer, geldig_tot, klasse, saldo, reiziger, allProduct);
    }
}