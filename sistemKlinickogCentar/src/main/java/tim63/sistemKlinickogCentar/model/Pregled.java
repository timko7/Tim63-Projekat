package tim63.sistemKlinickogCentar.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Pregled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DatumVreme", nullable = false)
    private Date datumVreme;

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

    public Pregled() {
    }

    public Pregled(Date datumVreme, int trajanjePregleda, Long idTipa, Long idSale, Long idLekara, double cena) {
        this.datumVreme = datumVreme;
        this.trajanjePregleda = trajanjePregleda;
        this.idTipa = idTipa;
        this.idSale = idSale;
        this.idLekara = idLekara;
        this.cena = cena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
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

    public void copyValues(Pregled pregled) {
        this.datumVreme = pregled.getDatumVreme();
        this.trajanjePregleda = pregled.getTrajanjePregleda();
        this.idTipa = pregled.getIdTipa();
        this.idSale = pregled.getIdSale();
        this.idLekara = pregled.getIdLekara();
        this.cena = pregled.getCena();
    }





}
