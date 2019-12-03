package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.TipPregleda;
import tim63.sistemKlinickogCentar.repository.TipPregledaRepositoryInterface;

import java.util.Collection;

@Service
public class TipPregledaService implements TipPregledaServiceInterface {

    @Autowired
    private TipPregledaRepositoryInterface repositoryTipPregleda;

    @Override
    public Collection<TipPregleda> findAll() {
        return repositoryTipPregleda.findAll();
    }

    @Override
    public TipPregleda findById(Long id) {
        return repositoryTipPregleda.findById(id).orElseGet(null);
    }

    @Override
    public TipPregleda findByNaziv(String naziv) {
        TipPregleda tip = repositoryTipPregleda.findByNazivTipa(naziv);
        return tip;
    }

    @Override
    public TipPregleda create(TipPregleda tipPregleda) throws Exception {

        TipPregleda ret = new TipPregleda();
        ret.copyValues(tipPregleda);
        ret = repositoryTipPregleda.save(ret);
        return ret;
    }

    @Override
    public TipPregleda update(TipPregleda tipPregleda) throws Exception {
        TipPregleda tipZaIzmenu = findById(tipPregleda.getId());
        tipZaIzmenu.copyValues(tipPregleda);
        tipZaIzmenu = repositoryTipPregleda.save(tipZaIzmenu);
        return tipZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        repositoryTipPregleda.deleteById(id);
    }

    @Override
    public void deleteByNaziv(String naziv) {
        TipPregleda tipZaObrisati = findByNaziv(naziv);
        repositoryTipPregleda.deleteById(tipZaObrisati.getId());
    }


}
