package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Klinika;

import java.util.Collection;

public interface KlinikaInterface {

    Collection<Klinika> findAll();

    Klinika findById(Long id);

    Klinika findByIme(String ime);

    Klinika create(Klinika klinika) throws Exception;

    Klinika update(Klinika klinika) throws Exception;

    void delete(Long id);

    Klinika izmeniPodatke(String stariNazivKlinike, Klinika klinika);
}
