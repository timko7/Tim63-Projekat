package tim63.sistemKlinickogCentar.model;


import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
public class Pacijent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "Ime", nullable = false)
    private  String ime;

    @Column(name = "Prezime", nullable = false)
    private  String prezime;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Grad", nullable = false)
    private String grad;

    @Column(name = "Drzava", nullable = false)
    private String drzava;

    @Column(name = "Adresa", nullable = false)
    private String adresa;

    @Column(name = "Telefon", nullable = false)
    private  String telefon;

    @Column(name = "Broj_Osiguranika", nullable = false)
    private  int broj_osiguranika;


    public Pacijent(String ime, String prezime, String email, String password, String grad,
                    String drzava, String adresa, String telefon, int broj_osiguranika) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.grad = grad;
        this.drzava = drzava;
        this.adresa = adresa;
        this.telefon = telefon;
        this.broj_osiguranika = broj_osiguranika;
    }

    public Pacijent() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGrad() {
        return grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public int getBroj_osiguranika() {
        return broj_osiguranika;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setBroj_osiguranika(int broj_osiguranika) {
        this.broj_osiguranika = broj_osiguranika;
    }

    public void copyValues(Pacijent pacijent) {
        this.ime=pacijent.getIme();
        this.prezime=pacijent.getPrezime();
        this.password=pacijent.getPassword();
        this.grad=pacijent.getGrad();
        this.drzava=pacijent.getDrzava();
        this.adresa=pacijent.getAdresa();
        this.telefon=pacijent.getTelefon();
        this.broj_osiguranika=pacijent.getBroj_osiguranika();
    }
}
