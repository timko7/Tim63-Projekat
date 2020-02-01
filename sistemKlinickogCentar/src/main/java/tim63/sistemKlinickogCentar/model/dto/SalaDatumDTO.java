package tim63.sistemKlinickogCentar.model.dto;

import tim63.sistemKlinickogCentar.model.Sala;

import java.time.LocalDateTime;

public class SalaDatumDTO {

    private Sala sala;
    private LocalDateTime datum;

    public SalaDatumDTO() {
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }
}
