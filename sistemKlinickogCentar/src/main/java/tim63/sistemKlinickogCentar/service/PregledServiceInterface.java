package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Pregled;

import java.util.Collection;

public interface PregledServiceInterface {

    Collection<Pregled> findAll();

    Pregled findById(Long id);

    Pregled create(Pregled pregled) throws Exception;

    void delete(Long id);
}
