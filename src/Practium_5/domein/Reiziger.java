package Practium_5.domein;

import java.sql.Date;
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

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
