package tim63.sistemKlinickogCentar.model.dto;

import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Lekar;

public class LekarKlinikaDTO {

    // TODO Create LekarDTO instead Lekar
    private Long lekar;

    // TODO Create KlinikaDTO instead Klinika
    private Long klinika;

    public LekarKlinikaDTO() {
    }

    public Long getLekar() {
        return lekar;
    }

    public void setLekar(Long lekar) {
        this.lekar = lekar;
    }

    public Long getKlinika() {
        return klinika;
    }

    public void setKlinika(Long klinika) {
        this.klinika = klinika;
    }
}
