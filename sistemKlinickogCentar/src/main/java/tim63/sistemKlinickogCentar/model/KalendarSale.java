package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class KalendarSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IdSale", nullable = false)
    private Long idSale;

    @Column(name = "DatumOd", nullable = false)
    private LocalDateTime datumOd;

    @Column(name = "DatumDo", nullable = false)
    private LocalDateTime datumDo;

    public KalendarSale() {
    }

    public KalendarSale(Long idSale, LocalDateTime datumOd, LocalDateTime datumDo) {
        this.idSale = idSale;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSale() {
        return idSale;
    }

    public void setIdSale(Long idSale) {
        this.idSale = idSale;
    }

    public LocalDateTime getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDateTime datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDateTime getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDateTime datumDo) {
        this.datumDo = datumDo;
    }

    public void copyValues(KalendarSale kalendarSale) {
        this.idSale = kalendarSale.getIdSale();
        this.datumOd = kalendarSale.getDatumOd();
        this.datumDo = kalendarSale.getDatumDo();
    }
}
