package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;


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
    private String broj_osiguranika;

    @Column(name = "idKlinike", nullable = false)
    private Long idKlinike;

    @Column(name = "idTipa", nullable = false)
    private Long idTipa;

    @Column(name = "Uloga", nullable = false)
    private Uloga uloga=Uloga.LEKAR;

    @Column(name = "Slobodan", nullable = false)
    private boolean slobodan;   // true-slobodan(nema zakazan termin), false-zauzet

    @Column(name = "RadnoVremeOd", nullable = false)
    private int radnoVremeOd;

    @Column(name = "RadnoVremeDo", nullable = false)
    private int radnoVremeDo;

    @Column(name = "Ocena", nullable = false)
    private int ocena=0;

    @Column(name = "SrednjaOcena", nullable = false)
    private double srednjaOcena=0;

    private int brojacIzvrsenihPregleda=0;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Klinika klinika;

    //private HashMap<LocalDateTime, Boolean> kalendar=new HashMap<LocalDateTime, Boolean>();


    public Lekar(String ime, String prezime, String email, String password, String grad,
                 String drzava, String adresa, String telefon, String broj_osiguranika, Long idKlinike,Long idTipa, boolean slobodan, int radnoVremeOd, int radnoVremeDo) {
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
        this.idTipa= idTipa;
        this.uloga = Uloga.LEKAR;
        this.slobodan = slobodan;
        this.radnoVremeOd = radnoVremeOd;
        this.radnoVremeDo = radnoVremeDo;
        this.ocena=0;
        this.srednjaOcena=0;
       // this.kalendar=napuniKalendar();
    }

    public Lekar() {
    }

    private   HashMap<LocalDateTime, Boolean> napuniKalendar(){
        HashMap<LocalDateTime, Boolean> k=new  HashMap<LocalDateTime, Boolean>();
        LocalDateTime sad=LocalDateTime.now();
        k.put(sad,true);
        for(int i=1;i<=365;i++){
            k.put(sad.plusDays(i),true);
        }
        return k;
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

    public String getBroj_osiguranika() {
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

    public void setBroj_osiguranika(String broj_osiguranika) {
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

    public Long getIdTipa() {
        return idTipa;
    }

    public void setIdTipa(Long idTipa) {
        this.idTipa = idTipa;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public double getSrednjaOcena() {
        return srednjaOcena;
    }

    public void setSrednjaOcena(double srednjaOcena) {
        this.srednjaOcena = srednjaOcena;
    }

    public int getBrojacIzvrsenihPregleda() {
        return brojacIzvrsenihPregleda;
    }

    public void setBrojacIzvrsenihPregleda(int brojacIzvrsenihPregleda) {
        this.brojacIzvrsenihPregleda = brojacIzvrsenihPregleda;
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
        this.idTipa= lekar.getIdTipa();
        this.uloga = lekar.getUloga();
        this.slobodan = lekar.isSlobodan();
        this.radnoVremeOd = lekar.getRadnoVremeOd();
        this.radnoVremeDo = lekar.getRadnoVremeDo();
        this.ocena=lekar.getOcena();
        this.srednjaOcena=lekar.getSrednjaOcena();
    }


}
