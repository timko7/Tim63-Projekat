package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;

import java.util.Collection;

public interface KalendarSaleServiceInterface {

    Collection<KalendarSale> findAll();

    Collection<KalendarSale> findByIdSale(Long id);

    KalendarSale createPoPredefinisanomPregledu(Pregled pregled);

    KalendarSale createPoPregleduOdZahteva(PregledOdZahteva pregledOdZahteva);

    void delete(Long id);

}
