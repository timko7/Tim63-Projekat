package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;

import java.util.Collection;

@Service
public class PregledService implements PregledServiceInterface {

    @Autowired
    private PregledRepositoryInterface repositoryPregled;

    @Override
    public Collection<Pregled> findAll() {
        return repositoryPregled.findAll();
    }

    @Override
    public Collection<Pregled> findByIdKlinike(Long id) {
        return repositoryPregled.findByIdKlinike(id);
    }

    @Override
    public Collection<Pregled> findByIdTipa(Long id) {
        return repositoryPregled.findByIdTipa(id);
    }

    @Override
    public Pregled findById(Long id) {
        return repositoryPregled.findById(id).orElseGet(null);
    }

    @Override
    public Pregled create(Pregled pregled) throws Exception {
        Pregled ret = new Pregled();
        ret.copyValues(pregled);
        ret = repositoryPregled.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {
        repositoryPregled.deleteById(id);
    }
}
