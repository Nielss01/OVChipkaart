package domein;



import Factories.DAOFactory;

import javax.persistence.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer", nullable = false)
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="reiziger_id")
    private Reiziger reiziger;
    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product",
            joinColumns = { @JoinColumn(name = "kaart_nummer") },
            inverseJoinColumns = { @JoinColumn(name = "product_nummer") })
    private List<Product> allProduct = new ArrayList<>();
    @Transient
    DAOFactory daoFactory = DAOFactory.newInstance();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public OVChipkaart() {

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
        daoFactory.getProductDAO().save(product);
    }

    public void deleteProduct(int product_nummer) {
        for (int i = 0; i < allProduct.size(); i++) {
            if (allProduct.get(i).getProduct_nummer() == product_nummer) {
                Product product = allProduct.get(i);
                allProduct.remove(product);
                daoFactory.getProductDAO().delete(product);
            }
        }
    }

    public void updateProduct(int product_nummer, double prijs) throws SQLException {
        for (int i = 0; i < allProduct.size(); i++) {
            if (allProduct.get(i).getProduct_nummer() == product_nummer) {
                allProduct.get(i).setPrijs(prijs);
                daoFactory.getProductDAO().update(allProduct.get(i));
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