package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Kalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Datum", nullable = false)
    private LocalDateTime datum;

    @Column(name = "Od", nullable = false)
    private LocalDateTime od;

    @Column(name = "Do", nullable = false)
    private LocalDateTime dokle;


    @Column(name = "IdLekara", nullable = false)
    private Long idLekara;

  /*  @Column(name = "IdPacijenta", nullable = false)
    private Long idPacijenta;

    @Column(name = "Odradjen", nullable = false)
    private boolean odradjen;
*/


    public Kalendar( LocalDateTime datum, LocalDateTime od, LocalDateTime dokle, Long idLekara) {
        this.id = id;
        this.datum = datum;
        this.od = od;
        this.dokle = dokle;
        this.idLekara = idLekara;
      //  this.idPacijenta=idPacijenta;
      //  this.odradjen=false;
    }

    public Kalendar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public LocalDateTime getOd() {
        return od;
    }

    public void setOd(LocalDateTime od) {
        this.od = od;
    }

    public LocalDateTime getDokle() {
        return dokle;
    }

    public void setDokle(LocalDateTime dokle) {
        this.dokle = dokle;
    }

    public Long getIdLekara() {
        return idLekara;
    }

    public void setIdLekara(Long idLekara) {
        this.idLekara = idLekara;
    }

  /*  public Long getIdPacijenta() {
        return idPacijenta;
    }

    public void setIdPacijenta(Long idPacijenta) {
        this.idPacijenta = idPacijenta;
    }

    public boolean isOdradjen() {
        return odradjen;
    }

    public void setOdradjen(boolean odradjen) {
        this.odradjen = odradjen;
    }*/

    public void copyValues(Kalendar kalendar) {
        this.datum = kalendar.getDatum();
        this.od = kalendar.getOd();
        this.dokle = kalendar.getDokle();
        this.idLekara = kalendar.getIdLekara();
      //  this.idPacijenta=kalendar.getIdPacijenta();
      //  this.odradjen=kalendar.isOdradjen();

    }
}
