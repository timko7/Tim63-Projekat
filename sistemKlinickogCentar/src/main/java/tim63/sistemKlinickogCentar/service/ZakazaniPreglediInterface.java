package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;

import java.util.Collection;

public interface ZakazaniPreglediInterface {

    Collection<ZaktaniPregledi> findAll();

    Collection<ZaktaniPregledi> findByIdLekara(Long id);

    Collection<ZaktaniPregledi> findByIdPacijenta(Long id);
    Collection<ZaktaniPregledi> findByIdKlinike(Long id);

    ZaktaniPregledi findById(Long id);

    ZaktaniPregledi update(ZaktaniPregledi pacijent) throws Exception;

    ZaktaniPregledi create(ZaktaniPregledi pregled) throws Exception;

    void delete(Long id);
}
