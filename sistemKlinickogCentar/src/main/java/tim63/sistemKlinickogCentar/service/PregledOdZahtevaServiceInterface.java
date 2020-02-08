package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.PregledOdZahteva;

import java.util.Collection;

public interface PregledOdZahtevaServiceInterface {

    Collection<PregledOdZahteva> findAll();

    Collection<PregledOdZahteva> findByIdKlinike(Long id);

    Collection<PregledOdZahteva> findByIdTipa(Long id);

    Collection<PregledOdZahteva> findByIdLekara(Long id);

    Collection<PregledOdZahteva> findByIdPacijenta(Long id);

    PregledOdZahteva findById(Long id);

    PregledOdZahteva update(PregledOdZahteva pregledOdZahteva) throws Exception;
    PregledOdZahteva pregledaj(PregledOdZahteva pregledOdZahteva) throws Exception;

    PregledOdZahteva create(PregledOdZahteva pregledOdZahteva) throws Exception;

    boolean okaziPregled(Long id);
    void delete(Long id);

}
