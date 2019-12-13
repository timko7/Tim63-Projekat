package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.repository.LekarRepositoryInterface;

import java.util.Collection;

@Service
public class LekarService implements LekarInterface {

    @Autowired
    public LekarRepositoryInterface lekRep;

    @Override
    public Collection<Lekar> findAll() {
        Collection<Lekar> result = lekRep.findAll();
        return result;
    }

    @Override
    public Lekar findById(Long id) {
        Lekar u = lekRep.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public Collection<Lekar> findByIdKlinike(Long idKlinike) {
        return lekRep.findByIdKlinike(idKlinike);
    }

    @Override
    public Collection<Lekar> findByIdTipa(Long idTipa) {
        return lekRep.findByIdTipa(idTipa);
    }

    @Override
    public Lekar findByEmail(String username) {
        Lekar u = lekRep.findByEmail(username);
        return u;
    }

    @Override
    public Lekar create(Lekar lekar) throws Exception {

        Lekar ret = new Lekar();
        lekar.setSlobodan(true);
        ret.copyValues(lekar);
        ret = lekRep.save(ret);
        return ret;
    }

    @Override
    public Lekar update(Lekar lekar) throws Exception {

        Lekar lekarZaIzmenu = findById(lekar.getId());
        lekarZaIzmenu.copyValues(lekar);
        lekarZaIzmenu = lekRep.save(lekarZaIzmenu);
        return lekarZaIzmenu;
    }

    @Override
    public Lekar dobuniLekara(Lekar lekar, Klinika klinika) throws Exception {
        Lekar l = findByEmail(lekar.getEmail());
        if (l.getKlinika() == null) {
            l.setKlinika(klinika);
        }
        l = this.lekRep.save(l);
        return l;
    }


    @Override
    public void delete(Long id) {
        lekRep.deleteById(id);
    }
}
