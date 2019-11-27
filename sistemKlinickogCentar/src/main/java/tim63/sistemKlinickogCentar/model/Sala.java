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


    public Sala() {
    }

    public Sala(String naziv) {
        this.naziv = naziv;
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

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void copyValues(Sala sala) {
        this.naziv = sala.getNaziv();
    }

}
