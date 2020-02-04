package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;

import java.util.Collection;
import java.util.List;

public interface KalendarServiceInterface {
    Collection<Kalendar> findAll();
    List<Kalendar> findByIdLekara(Long id);
   // List<Kalendar> findByIdPacijenta(Long id);
    Kalendar create(Pregled pregled) throws Exception;
    Kalendar createPrekoPesimistickih(PregledOdZahteva pregled) throws Exception;
    void delete(Long id);
}
