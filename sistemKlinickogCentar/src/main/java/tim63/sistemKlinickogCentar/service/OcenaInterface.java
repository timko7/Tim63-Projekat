package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Ocena;

import javax.validation.constraints.Max;
import java.util.Collection;
import java.util.List;

public interface OcenaInterface {

    Collection<Ocena> findAll();
    List<Ocena> findByIdLekara(Long id);
    Ocena create(Ocena pregled) throws Exception;
}
