package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;

@Entity
public class TipPregleda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NazivTipa", nullable = false)
    private String nazivTipa;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Klinika klinika;

    public TipPregleda() {
    }

    public TipPregleda(String nazivTipa, Klinika klinika) {
        this.nazivTipa = nazivTipa;
        this.klinika = klinika;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivTipa() {
        return nazivTipa;
    }

    public void setNazivTipa(String nazivTipa) {
        this.nazivTipa = nazivTipa;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public void copyValues(TipPregleda tipPregleda) {
        this.nazivTipa = tipPregleda.nazivTipa;
        this.klinika = tipPregleda.klinika;
    }


}
