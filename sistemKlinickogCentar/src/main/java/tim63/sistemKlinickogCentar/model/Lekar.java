package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;


@Entity
public class Lekar {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Prezime", nullable = false)
    private String prezime;

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
    private String telefon;

    @Column(name = "Broj_Osiguranika", nullable = false)
    private int broj_osiguranika;

    @Column(name = "idKlinike", nullable = false)
    private Long idKlinike;

    @Column(name = "Uloga", nullable = false)
    private Uloga uloga=Uloga.LEKAR;

    @Column(name = "Slobodan", nullable = false)
    private boolean slobodan;   // true-slobodan(nema zakazan termin), false-zauzet

    @Column(name = "RadnoVremeOd", nullable = false)
    private int radnoVremeOd;

    @Column(name = "RadnoVremeDo", nullable = false)
    private int radnoVremeDo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Klinika klinika;

    public Lekar(String ime, String prezime, String email, String password, String grad,
                 String drzava, String adresa, String telefon, int broj_osiguranika, Long idKlinike, boolean slobodan, int radnoVremeOd, int radnoVremeDo) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.grad = grad;
        this.drzava = drzava;
        this.adresa = adresa;
        this.telefon = telefon;
        this.broj_osiguranika = broj_osiguranika;
        this.idKlinike = idKlinike;
        this.uloga = Uloga.LEKAR;
        this.slobodan = slobodan;
        this.radnoVremeOd = radnoVremeOd;
        this.radnoVremeDo = radnoVremeDo;
    }

    public Lekar() {
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

    public Klinika getKlinika() {
        return klinika;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public boolean isSlobodan() {
        return slobodan;
    }

    public void setSlobodan(boolean slobodan) {
        this.slobodan = slobodan;
    }

    public int getRadnoVremeOd() {
        return radnoVremeOd;
    }

    public void setRadnoVremeOd(int radnoVremeOd) {
        this.radnoVremeOd = radnoVremeOd;
    }

    public int getRadnoVremeDo() {
        return radnoVremeDo;
    }

    public void setRadnoVremeDo(int radnoVremeDo) {
        this.radnoVremeDo = radnoVremeDo;
    }

    public void copyValues(Lekar lekar) {
        this.ime = lekar.getIme();
        this.prezime = lekar.getPrezime();
        this.email = lekar.getEmail();
        this.password = lekar.getPassword();
        this.grad = lekar.getGrad();
        this.drzava = lekar.getDrzava();
        this.adresa = lekar.getAdresa();
        this.telefon = lekar.getTelefon();
        this.broj_osiguranika = lekar.getBroj_osiguranika();
        this.idKlinike = lekar.getIdKlinike();
        this.uloga = lekar.getUloga();
        this.slobodan = lekar.isSlobodan();
        this.radnoVremeOd = lekar.getRadnoVremeOd();
        this.radnoVremeDo = lekar.getRadnoVremeDo();
    }


}
