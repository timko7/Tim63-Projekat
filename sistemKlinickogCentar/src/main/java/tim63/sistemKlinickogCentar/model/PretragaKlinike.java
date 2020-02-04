package tim63.sistemKlinickogCentar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PretragaKlinike {

    private String adresa;
    private  Long idTipa;
    private  Long idKlinike;
    private LocalDate termin;

    private String ime;
    private String prezime;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Long getIdTipa() {
        return idTipa;
    }

    public void setIdTipa(Long idTipa) {
        this.idTipa = idTipa;
    }

    public Long getIdKlinike() {
        return idKlinike;
    }

    public void setIdKlinike(Long idKlinike) {
        this.idKlinike = idKlinike;
    }

    public LocalDate getTermin() {
        return termin;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setTermin(LocalDate termin) {
        this.termin = termin;
    }

    public PretragaKlinike() {
    }

    public PretragaKlinike(String adresa, Long idTipa, Long idKlinike, LocalDate termin) {
        this.adresa = adresa;
        this.idTipa = idTipa;
        this.idKlinike = idKlinike;
        this.termin = termin;
    }
    public PretragaKlinike(String ime, String prezime,Long idTipa, Long idKlinike, LocalDate termin) {
        this.ime = ime;
        this.prezime=prezime;
        this.idTipa = idTipa;
        this.idKlinike = idKlinike;
        this.termin = termin;
    }
}
