package domein;

import Factories.DAOFactory;

import javax.persistence.*;

@Entity
@Table(name="adres")
public class Adres {
    @Id
    @Column(name = "adres_id", nullable = false)
    private Long id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    @OneToOne
    @JoinColumn (name="reiziger_id")
    private Reiziger reiziger;
    @Transient
    private DAOFactory daoFactory = DAOFactory.newInstance();


    public Adres(Long id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public Adres() {

    }


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
        daoFactory.getAdresDAO().update(this);
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }


    @Override
    public String toString() {
        return id + " "+ postcode + " " +
                huisnummer + " " +
                straat + " " +
                woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }
}
