package tim63.sistemKlinickogCentar.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.OcenaKlinike;
import tim63.sistemKlinickogCentar.repository.OcenaKlinikeRepsitory;

import java.util.Collection;
import java.util.List;

@Service
public class OcenaKlinikeService implements OcenaKlinikeInterface {

    @Autowired
    private OcenaKlinikeRepsitory repositoryOcena;
    @Override
    public Collection<OcenaKlinike> findAll() {
        return repositoryOcena.findAll();
    }

    @Override
    public List<OcenaKlinike> findByIdKlinike(Long id) {
        return repositoryOcena.findByIdKlinike(id);
    }

    @Override
    public OcenaKlinike create(OcenaKlinike pregled) throws Exception {
        OcenaKlinike ret = new OcenaKlinike();
        ret.setOcena(pregled.getOcena());
        ret.setIdKlinike(pregled.getIdKlinike());
        ret = repositoryOcena.save(ret);
        return ret;
    }
}
