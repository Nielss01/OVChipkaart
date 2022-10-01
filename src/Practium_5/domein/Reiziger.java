package Practium_5.domein;

import Practium_5.Factory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> mijnOVChipkaarten = new ArrayList<>();
    Factory factory = Factory.newInstance();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
    public void createNewOvChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo) throws SQLException {
        OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer,geldig_tot, klasse,saldo);
        if(!mijnOVChipkaarten.contains(ovChipkaart)){
            this.mijnOVChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
            factory.getOvChipkaartDAO().save(ovChipkaart);
        }
    }
    public void deleteOvChipkaart(int id) throws SQLException {
        for (int i = 0; i < mijnOVChipkaarten.size(); i++) {
            if (mijnOVChipkaarten.get(i).getKaart_nummer() == id) {
                factory.getOvChipkaartDAO().delete(mijnOVChipkaarten.get(i));
                mijnOVChipkaarten.remove(mijnOVChipkaarten.get(i));
            }
        }
    }

    public void updateOvChipkaart(int id, double saldo){
        for (int i = 0; i < mijnOVChipkaarten.size(); i++) {
            if (mijnOVChipkaarten.get(i).getKaart_nummer() == id) {
                factory.getOvChipkaartDAO().update(mijnOVChipkaarten.get(i), saldo);
                mijnOVChipkaarten.get(i).setSaldo(saldo);
            }
        }
    }

    public Adres createNewAdres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats ) throws SQLException {
        this.adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats);
        adres.setReiziger(this);
        factory.getAdresDAO().save(adres);
        return adres;
    }
    public void deleteAdres(){
        factory.getAdresDAO().delete(this.adres);
        this.adres = null;
    }
    public void setId(int id) {
        factory.getReizigerDAO().update(this,id);
        this.id = id;
    }
    public void setNewAdres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats);
        this.adres.setReiziger(this);
        factory.getAdresDAO().update(this.adres);
    }




    public int getId() {
        return id;
    }


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
                "#" + id +
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
}
