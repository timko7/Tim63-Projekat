package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;

@Entity
public class AdminKlinike {
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Klinika klinika;

    public AdminKlinike(String ime, String prezime, String email, String password, String grad, String drzava, String adresa, String telefon, Klinika klinika) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.grad = grad;
        this.drzava = drzava;
        this.adresa = adresa;
        this.telefon = telefon;
        this.klinika = klinika;
    }

    public AdminKlinike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public void copyValues(AdminKlinike adminKlinike) {
        this.ime = adminKlinike.getIme();
        this.prezime = adminKlinike.getPrezime();
        this.email = adminKlinike.getEmail();
        this.password = adminKlinike.getPassword();
        this.grad = adminKlinike.getGrad();
        this.drzava = adminKlinike.getDrzava();
        this.adresa = adminKlinike.getAdresa();
        this.telefon = adminKlinike.getTelefon();
        this.klinika = adminKlinike.getKlinika();
    }


}
