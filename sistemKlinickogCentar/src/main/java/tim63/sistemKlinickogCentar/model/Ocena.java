package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Ocena", nullable = false)
    private double ocena;

    @Column(name = "IdLekara", nullable = false)
    private Long idLekara;

    public Ocena(double ocena, Long idLekara) {
        this.ocena = ocena;
        this.idLekara = idLekara;
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

    public Long getIdLekara() {
        return idLekara;
    }

    public void setIdLekara(Long idLekara) {
        this.idLekara = idLekara;
    }

    public Ocena() {
    }
}
