package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.OcenaKlinike;

import java.util.Collection;
import java.util.List;

public interface OcenaKlinikeInterface {
    Collection<OcenaKlinike> findAll();
    List<OcenaKlinike> findByIdKlinike(Long id);
    OcenaKlinike create(OcenaKlinike pregled) throws Exception;
}
