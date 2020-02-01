package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PregledOdZahteva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long verzija;

    @Column(name = "DatumVreme", nullable = false)
    private LocalDateTime datumVreme;

    @Column(name = "TrajanjePregleda", nullable = false)
    private int trajanjePregleda; // u minutima

    @Column(name = "IdTipa", nullable = false)
    private Long idTipa;

    @Column(name = "IdSale", nullable = false)
    private Long idSale;

    @Column(name = "IdLekara", nullable = false)
    private Long idLekara;

    @Column(name = "Cena", nullable = false)
    private double cena;

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    @Column(name = "IdPacijenta", nullable = false)
    private Long idPacijenta;

    @Column(name = "Odradjen", nullable = false)
    private boolean odradjen=false;

    public PregledOdZahteva() {
    }

    public PregledOdZahteva(LocalDateTime datumVreme, int trajanjePregleda, Long idTipa, Long idSale, Long idLekara, double cena, Long idKlinike, Long idPacijenta, boolean odradjen) {
        this.datumVreme = datumVreme;
        this.trajanjePregleda = trajanjePregleda;
        this.idTipa = idTipa;
        this.idSale = idSale;
        this.idLekara = idLekara;
        this.cena = cena;
        this.idKlinike = idKlinike;
        this.idPacijenta = idPacijenta;
        this.odradjen = odradjen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVerzija() {
        return verzija;
    }

    public void setVerzija(Long verzija) {
        this.verzija = verzija;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public int getTrajanjePregleda() {
        return trajanjePregleda;
    }

    public void setTrajanjePregleda(int trajanjePregleda) {
        this.trajanjePregleda = trajanjePregleda;
    }

    public Long getIdTipa() {
        return idTipa;
    }

    public void setIdTipa(Long idTipa) {
        this.idTipa = idTipa;
    }

    public Long getIdSale() {
        return idSale;
    }

    public void setIdSale(Long idSale) {
        this.idSale = idSale;
    }

    public Long getIdLekara() {
        return idLekara;
    }

    public void setIdLekara(Long idLekara) {
        this.idLekara = idLekara;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public Long getIdPacijenta() {
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
    }

    public void copyValues(PregledOdZahteva pregledOdZahteva){
        this.datumVreme = pregledOdZahteva.getDatumVreme();
        this.trajanjePregleda = pregledOdZahteva.getTrajanjePregleda();
        this.idTipa = pregledOdZahteva.getIdTipa();
        this.idSale = pregledOdZahteva.getIdSale();
        this.idLekara = pregledOdZahteva.getIdLekara();
        this.cena = pregledOdZahteva.getCena();
        this.idKlinike = pregledOdZahteva.getIdKlinike();
        this.idPacijenta = pregledOdZahteva.getIdPacijenta();
        this.odradjen = pregledOdZahteva.isOdradjen();
    }



}
