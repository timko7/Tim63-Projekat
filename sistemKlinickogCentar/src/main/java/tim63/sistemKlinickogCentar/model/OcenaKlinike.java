package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;

@Entity
public class OcenaKlinike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Ocena", nullable = false)
    private double ocena;

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    public OcenaKlinike(double ocena, Long idKlinike) {
        this.ocena = ocena;
        this.idKlinike = idKlinike;
    }

    public OcenaKlinike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }
}
