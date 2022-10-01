package Practium_5.domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;
    private Reiziger reiziger;
    private final List<Product> allProduct = new ArrayList<>();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
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

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

     public void addProduct(Product product){
        allProduct.add(product);
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
                saldo +" "+ "| ReizigerID: " +
                 reiziger_id +" ";
    }

    public List<Product> getAllProduct() {
        return allProduct;
    }
}
