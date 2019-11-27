package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Sala;

import java.util.Collection;

public interface SalaSetviceInterface {
    Collection<Sala> findAll();

    Sala findById(Long id);

    Sala findByNaziv(String naziv);

    Sala create(Sala sala) throws Exception;

    Sala update(Sala sala) throws Exception;

    void delete(Long id);

    void deleteByNaziv(String naziv);
}
