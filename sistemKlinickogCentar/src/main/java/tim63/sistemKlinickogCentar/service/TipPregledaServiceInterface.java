package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.TipPregleda;

import java.util.Collection;

public interface TipPregledaServiceInterface {
    Collection<TipPregleda> findAll();

    TipPregleda findById(Long id);

    TipPregleda findByNaziv(String naziv);

    TipPregleda create(TipPregleda tipPregleda) throws Exception;

    TipPregleda update(TipPregleda tipPregleda) throws Exception;

    void delete(Long id);

    void deleteByNaziv(String email);

    Collection<TipPregleda> findByIdKlinike(Long idKlinike);
}
