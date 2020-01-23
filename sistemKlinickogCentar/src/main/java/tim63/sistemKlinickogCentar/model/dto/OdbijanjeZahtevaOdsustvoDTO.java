package tim63.sistemKlinickogCentar.model.dto;

import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;

public class OdbijanjeZahtevaOdsustvoDTO {

    private ZahtevOdsustvo zahtevOdsustvo;
    private String razlogOdbijanja;

    public OdbijanjeZahtevaOdsustvoDTO() {
    }

    public ZahtevOdsustvo getZahtevOdsustvo() {
        return zahtevOdsustvo;
    }

    public void setZahtevOdsustvo(ZahtevOdsustvo zahtevOdsustvo) {
        this.zahtevOdsustvo = zahtevOdsustvo;
    }

    public String getRazlogOdbijanja() {
        return razlogOdbijanja;
    }

    public void setRazlogOdbijanja(String razlogOdbijanja) {
        this.razlogOdbijanja = razlogOdbijanja;
    }
}
