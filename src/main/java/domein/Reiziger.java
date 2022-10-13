package domein;


import Factories.DAOFactory;

import javax.persistence.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id", nullable = false)
    private Integer reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    @OneToOne(mappedBy = "reiziger")
    private Adres adres;
    @OneToMany(mappedBy = "reiziger")
    private List<OVChipkaart> mijnOVChipkaarten = new ArrayList<>();
    @Transient
    private DAOFactory daoFactory = DAOFactory.newInstance();

    public Reiziger(int reiziger_id,String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {

    }

    public void createNewOvChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo)  {
        OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer,geldig_tot, klasse,saldo);
        if(!mijnOVChipkaarten.contains(ovChipkaart)){
            this.mijnOVChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
            daoFactory.getOvChipkaartDAO().save(ovChipkaart);
        }
    }

    public void createNewOVChipkaartWithProduct(int kaart_nummer, Date geldig_tot, int klasse, double saldo,int product_nummer, String naam, String beschrijving, double prijs) {
        OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer,geldig_tot, klasse,saldo);
        Product product = new Product(product_nummer,naam, beschrijving, prijs);
        if(!mijnOVChipkaarten.contains(ovChipkaart)){
            this.mijnOVChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
            ovChipkaart.addProduct(product);
            daoFactory.getOvChipkaartDAO().save(ovChipkaart);
        }
    }


    public void deleteOvChipkaart(int id)  {
        for (int i = 0; i < mijnOVChipkaarten.size(); i++) {
            if (mijnOVChipkaarten.get(i).getKaart_nummer() == id) {
                OVChipkaart ovChipkaart = mijnOVChipkaarten.get(i);
                mijnOVChipkaarten.remove(ovChipkaart);
                daoFactory.getOvChipkaartDAO().delete(ovChipkaart);
            }
        }
    }

    public void updateOvChipkaart(int id, double saldo)  {
        for (int i = 0; i < mijnOVChipkaarten.size(); i++) {
            if (mijnOVChipkaarten.get(i).getKaart_nummer() == id) {
                mijnOVChipkaarten.get(i).setSaldo(saldo);
                daoFactory.getOvChipkaartDAO().update(mijnOVChipkaarten.get(i));
            }
        }
    }

    public void createNewAdres(Long adres_id, String postcode, String huisnummer, String straat, String woonplaats ) throws SQLException {
        this.adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats);
        adres.setReiziger(this);
        daoFactory.getAdresDAO().save(adres);

    }
    public void deleteAdres(){
        daoFactory.getAdresDAO().delete(adres);
        this.adres = null;

    }

//    public void setNewAdres(long adres_id, String postcode, String huisnummer, String straat, String woonplaats) {
//        this.adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats);
//        this.adres.setReiziger(this);
//        daoFactory.getAdresDAO().update(adres);
//    }



    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
        daoFactory.getReizigerDAO().update(this);

    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        String s = "Reiziger" +
                "#" + reiziger_id +
                ": " + voorletters +
                ". " + tussenvoegsel +" " +
                achternaam+ " " +
                geboortedatum;
        if(adres != null){
            s= s + ", Adres: " + adres;
        }
        if(!mijnOVChipkaarten.isEmpty()){
            s = s + " | " +
                    "OVChipkaarten: " + mijnOVChipkaarten;
        }
        return s;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getMijnOVChipkaarten() {
        return mijnOVChipkaarten;
    }

    public void setMijnOVChipkaarten(List<OVChipkaart> mijnOVChipkaarten) {
        this.mijnOVChipkaarten = mijnOVChipkaarten;
    }

    public void updateProductforReiziger(int id, int product_nummer, double prijs) throws SQLException {
        for (int i = 0; i < mijnOVChipkaarten.size(); i++) {
            if (mijnOVChipkaarten.get(i).getKaart_nummer() == id) {
                mijnOVChipkaarten.get(i).updateProduct(product_nummer, prijs);
            }
        }
    }

    public Integer getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(Integer reiziger_id) {
        this.reiziger_id = reiziger_id;
    }
}
