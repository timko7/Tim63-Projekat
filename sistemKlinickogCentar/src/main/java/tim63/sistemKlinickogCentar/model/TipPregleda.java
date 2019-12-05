package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;

@Entity
public class TipPregleda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NazivTipa", nullable = false)
    private String nazivTipa;

    @Column(name = "Slobodan", nullable = false)
    private boolean slobodan;   // true-slobodan tip, false-zauzet tip/ima zakazan pregled

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    public TipPregleda() {
    }

    public TipPregleda(String nazivTipa, Klinika klinika) {
        this.nazivTipa = nazivTipa;
    }

    public TipPregleda(String nazivTipa, boolean slobodan, Long idKlinike) {
        this.nazivTipa = nazivTipa;
        this.slobodan = slobodan;
        this.idKlinike = idKlinike;
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

    public boolean isSlobodan() {
        return slobodan;
    }

    public void setSlobodan(boolean slobodan) {
        this.slobodan = slobodan;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public void copyValues(TipPregleda tipPregleda) {
        this.nazivTipa = tipPregleda.getNazivTipa();
        this.slobodan = tipPregleda.isSlobodan();
        this.idKlinike = tipPregleda.getIdKlinike();
    }


}
