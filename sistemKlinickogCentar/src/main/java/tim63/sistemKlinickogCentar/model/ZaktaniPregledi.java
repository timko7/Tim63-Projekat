package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Version;

@Entity

public class ZaktaniPregledi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DatumVreme", nullable = false)
    private LocalDateTime datumVreme;

    @Column(name = "TrajanjePregleda", nullable = false)
    private int trajanjePregleda; // u minutima

    @Column(name = "IdTipa", nullable = false)
    private Long idTipa;

    @Column(name = "IdSale", nullable = true)
    private Long idSale;

    @Column(name = "IdLekara", nullable = false)
    private Long idLekara;

    @Column(name = "Cena", nullable = false)
    private double cena;

    @Column(name = "IdKlinike", nullable = false)
    private Long idKlinike;

    @Column(name = "idPacijenta", nullable = false)
    private Long idPacijenta;

    @Column(name = "Odradjen", nullable = false)
    private boolean odradjen=false;

    @Column(name = "Rezervisan", nullable = false)
    private boolean rezervisan=false;

    @Column(name = "Odobren", nullable = false)
    private boolean odobren=false;

    @Version
    private Long version;


    public ZaktaniPregledi(LocalDateTime datumVreme,  Long idTipa,  Long idLekara, double cena, Long idKlinike, Long idPacijenta) {

        this.datumVreme = datumVreme;
      this.trajanjePregleda = 0;
        this.idTipa = idTipa;
        //this.idSale = idSale;
        this.idLekara = idLekara;
        this.cena = cena;
        this.idKlinike = idKlinike;
        this.idPacijenta = idPacijenta;
        this.odradjen = false;
        this.odobren=false;
     //   this.rezervisan=false;
    }

    public ZaktaniPregledi() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }

    public boolean isRezervisan() {
        return rezervisan;
    }

    public void setRezervisan(boolean rezervisan) {
        this.rezervisan = rezervisan;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void copyValues(ZaktaniPregledi pregled) {
        this.datumVreme = pregled.getDatumVreme();
        this.trajanjePregleda = pregled.getTrajanjePregleda();
        this.idTipa = pregled.getIdTipa();
        this.idSale = pregled.getIdSale();
        this.idLekara = pregled.getIdLekara();
        this.cena = pregled.getCena();
        this.idKlinike = pregled.getIdKlinike();
        this.idPacijenta=pregled.getIdPacijenta();
        this.odradjen=pregled.isOdradjen();
       this.rezervisan=pregled.isRezervisan();
       this.odobren=pregled.isOdobren();
    }
}
