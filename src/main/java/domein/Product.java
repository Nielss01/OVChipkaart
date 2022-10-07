package domein;



import javax.persistence.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="PRODUCT")
public class Product {
    @Id
    @Column(name = "product_nummer", nullable = false)
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    @ManyToMany(mappedBy="allProduct")
    private final List<OVChipkaart> allOvChipkaart = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public void addOVChipkaartAndSave(OVChipkaart ovChipkaart) throws SQLException {
        allOvChipkaart.add(ovChipkaart);
    }

    public void deleteOvChipkaartP(OVChipkaart ovChipkaart) throws SQLException {
        allOvChipkaart.remove(ovChipkaart);
    }
    public void deleteProduct() throws SQLException {
        Product product = null;
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
