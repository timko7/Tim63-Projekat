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

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    public Sala() {
    }

    public Sala(String naziv) {
        this.naziv = naziv;
    }

    public Sala(String naziv, boolean slobodna) {
        this.naziv = naziv;
        this.slobodna = slobodna;
    }

    public Sala(String naziv, boolean slobodna, Long idKlinike) {
        this.naziv = naziv;
        this.slobodna = slobodna;
        this.idKlinike = idKlinike;
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

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public void copyValues(Sala sala) {
        this.naziv = sala.getNaziv();
        this.slobodna = sala.isSlobodna();
        this.idKlinike = sala.getIdKlinike();
    }

}
