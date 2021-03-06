package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Klinika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Adresa", nullable = false)
    private String adresa;

    @Column(name = "Opis", nullable = false)
    private String opis;

    @Column(name = "SrednjaOcena", nullable = false)
    private double srednjaOcena=0;


    @OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Lekar> lekari = new HashSet<>();

    // @Column(name = "AdminKlinike", nullable = false)
    // private  AdminKlinike adminKlinike;

    public Klinika(String ime, String adresa, String opis) {
        this.ime = ime;
        this.adresa = adresa;
        this.opis = opis;
       this.srednjaOcena=0;
    }

    public Klinika() {

    }


    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getOpis() {
        return opis;
    }

    // public AdminKlinike getAdminKlinike() {
    //   return adminKlinike;
    //}

    public void setId(Long id) {
        this.id = id;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    // public void setAdminKlinike(AdminKlinike adminKlinike) {
    //   this.adminKlinike = adminKlinike;
    //}


    public double getSrednjaOcena() {
        return srednjaOcena;
    }

    public void setSrednjaOcena(double srednjaOcena) {
        this.srednjaOcena = srednjaOcena;
    }



    public Set<Lekar> getLekari() {
        return lekari;
    }

    public void setLekari(Set<Lekar> lekari) {
        this.lekari = lekari;
    }

    public void copyValues(Klinika klinika) {
        //  this.adminKlinike = klinika.getAdminKlinike();
        this.adresa = klinika.getAdresa();
        this.ime = klinika.getIme();
        this.opis = klinika.getOpis();
        this.srednjaOcena=klinika.getSrednjaOcena();

    }

}
