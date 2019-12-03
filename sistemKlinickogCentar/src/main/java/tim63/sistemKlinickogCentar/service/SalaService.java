package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;

import java.util.Collection;

@Service
public class SalaService implements SalaSetviceInterface {

    @Autowired
    private SalaRepositoryInterface repositorySala;

    @Override
    public Collection<Sala> findAll() {
        return repositorySala.findAll();
    }

    @Override
    public Sala findById(Long id) {
        return repositorySala.findById(id).orElseGet(null);
    }

    @Override
    public Collection<Sala> findByIdKlinike(Long id) {
        return repositorySala.findByIdKlinike(id);
    }

    @Override
    public Sala findByNaziv(String naziv) {
        Sala sala = repositorySala.findByNaziv(naziv);
        return sala;
    }

    @Override
    public Sala create(Sala sala) throws Exception {
        Sala ret = new Sala();
        sala.setSlobodna(true);
        ret.copyValues(sala);
        ret = repositorySala.save(ret);
        return ret;
    }

    @Override
    public Sala update(Sala sala) throws Exception {
        Sala salaZaIzmenu = findById(sala.getId());
        salaZaIzmenu.copyValues(sala);
        salaZaIzmenu = repositorySala.save(salaZaIzmenu);
        return salaZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        repositorySala.deleteById(id);
    }

    @Override
    public void deleteByNaziv(String naziv) {
        Sala salaZaObrisati = findByNaziv(naziv);
        repositorySala.deleteById(salaZaObrisati.getId());
    }
}
