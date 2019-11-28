package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Naziv", nullable = false)
    private String naziv;

    //TODO dodaj rezervisana bool ....
    @Column(name = "Slobodna", nullable = false)
    private boolean slobodna;   // true-slobodna, false-zauzeta

    public Sala() {
    }

    public Sala(String naziv) {
        this.naziv = naziv;
    }

    public Sala(String naziv, boolean slobodna) {
        this.naziv = naziv;
        this.slobodna = slobodna;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean isSlobodna() {
        return slobodna;
    }

    public void setSlobodna(boolean slobodna) {
        this.slobodna = slobodna;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void copyValues(Sala sala) {
        this.naziv = sala.getNaziv();
        this.slobodna = sala.isSlobodna();
    }

}
