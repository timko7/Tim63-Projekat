package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class PregledService implements PregledServiceInterface {

    @Autowired
    private PregledRepositoryInterface repositoryPregled;

    @Autowired
    private SalaService salaService;

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
    public Collection<Pregled> findByIdLekara(Long id) {
        return repositoryPregled.findByIdLekara(id);
    }

    @Override
    public Collection<Pregled> findByIdPacijenta(Long id) {
        return repositoryPregled.findByIdPacijenta(id);
    }

    @Override
    public Pregled findById(Long id) {
        return repositoryPregled.findById(id).orElseGet(null);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Pregled update(Pregled pregled) throws Exception {
        Pregled zaIzmenu = findById(pregled.getId());
        zaIzmenu.copyValuesZaRezervaciju(pregled);
        zaIzmenu = repositoryPregled.save(zaIzmenu);
        return zaIzmenu;
    }

    @Override
    @Transactional(readOnly = false)
    public Pregled create(Pregled pregled) throws Exception {
        Pregled ret = new Pregled();
        Sala salaTemp = new Sala();

        salaTemp = salaService.findById(pregled.getIdSale());
        salaTemp.setSlobodna(false);
        salaService.update(salaTemp);


        ret.copyValues(pregled);
        ret = repositoryPregled.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {
        repositoryPregled.deleteById(id);
    }
}
