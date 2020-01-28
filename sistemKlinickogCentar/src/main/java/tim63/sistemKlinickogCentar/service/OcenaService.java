package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Ocena;
import tim63.sistemKlinickogCentar.repository.OcenaRepository;

import java.util.Collection;
import java.util.List;

@Service
public class OcenaService implements OcenaInterface {

    @Autowired
    private OcenaRepository repositoryOcena;

    @Override
    public Collection<Ocena> findAll() {
        return repositoryOcena.findAll();
    }

    @Override
    public List<Ocena> findByIdLekara(Long id) {
        return repositoryOcena.findByIdLekara(id);
    }

    @Override
    public Ocena create(Ocena pregled) throws Exception {
        Ocena ret = new Ocena();
        ret.setOcena(pregled.getOcena());
        ret.setIdLekara(pregled.getIdLekara());
        ret = repositoryOcena.save(ret);
        return ret;
    }
}
