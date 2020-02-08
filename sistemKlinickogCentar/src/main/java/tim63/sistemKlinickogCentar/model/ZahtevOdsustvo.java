package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class ZahtevOdsustvo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DatumPocetka", nullable = false)
    private LocalDate datumPocetka;

    @Column(name = "DatumZavrsetka", nullable = false)
    private LocalDate datumZavrsetka;

    @Column(name = "IdLekara", nullable = false)
    private Long idLekara;

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    @Column(name = "Obradjen", nullable = false)
    private boolean obradjen;   // true - zahtev je obradjen

    @Column(name = "Prihvacen", nullable = false)
    private boolean prihvacen;  // true - zahtev je prihvacen/odobren

    @Version
    private Long version;

    public ZahtevOdsustvo() {
    }

    public ZahtevOdsustvo(LocalDate datumPocetka, LocalDate datumZavrsetka, Long idLekara, Long idKlinike, boolean prihvacen, boolean obradjen) {
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.idLekara = idLekara;
        this.idKlinike = idKlinike;
        this.obradjen = obradjen;
        this.prihvacen = prihvacen;
    }

    public ZahtevOdsustvo(Long id,LocalDate datumPocetka, LocalDate datumZavrsetka, Long idLekara, Long idKlinike, boolean prihvacen, boolean obradjen) {
        this.id=id;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.idLekara = idLekara;
        this.idKlinike = idKlinike;
        this.obradjen = obradjen;
        this.prihvacen = prihvacen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(LocalDate datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public LocalDate getDatumZavrsetka() {
        return datumZavrsetka;
    }

    public void setDatumZavrsetka(LocalDate datumZavrsetka) {
        this.datumZavrsetka = datumZavrsetka;
    }

    public Long getIdLekara() {
        return idLekara;
    }

    public void setIdLekara(Long idLekara) {
        this.idLekara = idLekara;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public boolean isObradjen() {
        return obradjen;
    }

    public void setObradjen(boolean obradjen) {
        this.obradjen = obradjen;
    }

    public boolean isPrihvacen() {
        return prihvacen;
    }

    public void setPrihvacen(boolean prihvacen) {
        this.prihvacen = prihvacen;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void copyValues(ZahtevOdsustvo zahtevOdsustvo) {
        this.datumPocetka = zahtevOdsustvo.getDatumPocetka();
        this.datumZavrsetka = zahtevOdsustvo.getDatumZavrsetka();
        this.idKlinike = zahtevOdsustvo.getIdKlinike();
        this.idLekara = zahtevOdsustvo.getIdLekara();
        this.obradjen = zahtevOdsustvo.isObradjen();
        this.prihvacen = zahtevOdsustvo.isPrihvacen();
    }

    @Override
    public String toString() {
        return "ZahtevOdsustvo{" +
                "id=" + id +
                ", datumPocetka=" + datumPocetka +
                ", datumZavrsetka=" + datumZavrsetka +
                ", idLekara=" + idLekara +
                ", idKlinike=" + idKlinike +
                ", obradjen=" + obradjen +
                ", prihvacen=" + prihvacen +
                '}';
    }
}
