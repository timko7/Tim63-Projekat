package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Pacijent;

import java.util.Collection;

public interface PacijentInterface {

    Collection<Pacijent> findAll();

    Pacijent findById(Long id);

    Pacijent findByEmail(String username);

    Pacijent create(Pacijent pacijent) throws Exception;

    Pacijent update(Pacijent pacijent) throws Exception;


    void delete(Long id);
}
