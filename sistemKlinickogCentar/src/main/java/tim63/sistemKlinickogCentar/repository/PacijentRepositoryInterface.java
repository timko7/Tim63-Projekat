package tim63.sistemKlinickogCentar.repository;

import tim63.sistemKlinickogCentar.model.Pacijent;

import java.util.HashMap;

public interface PacijentRepositoryInterface {
    HashMap<Long,Pacijent> findAll();

    Pacijent findOne(Long id);

    Pacijent create(Pacijent pacijent) throws Exception;

    Pacijent update(Pacijent pacijent) throws Exception;

    void delete(Long id);
}
