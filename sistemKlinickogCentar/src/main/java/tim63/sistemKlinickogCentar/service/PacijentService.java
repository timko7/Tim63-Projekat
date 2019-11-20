package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.repository.PacijentRepositoryInterface;

import java.util.Collection;
import java.util.Optional;

@Service
public class PacijentService implements  PacijentInterface {

    @Autowired
    public  PacijentRepositoryInterface pacRep;


    @Override
    public Collection<Pacijent> findAll() {
        Collection<Pacijent> result = pacRep.findAll();
        return result;
    }

    @Override
    public Pacijent findById(Long id) {
        Pacijent u = pacRep.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public Pacijent findByEmail(String username) {
        Pacijent u = pacRep.findByEmail(username);
        return u;
    }

    @Override
    public Pacijent create(Pacijent pacijent) throws Exception {

        Pacijent u = new Pacijent();

        u.setIme(pacijent.getIme());
        u.setPrezime(pacijent.getPrezime());
        u.setEmail(pacijent.getEmail());
        u.setPassword(pacijent.getPassword());
        u.setGrad(pacijent.getGrad());
        u.setDrzava(pacijent.getDrzava());
        u.setAdresa(pacijent.getAdresa());
        u.setTelefon(pacijent.getTelefon());
        u.setBroj_osiguranika(pacijent.getBroj_osiguranika());

        u = this.pacRep.save(u);
        return u;
    }

    @Override
    public Pacijent update(Pacijent pacijent) throws Exception {

            Pacijent pacijentZaIzmenu=findById(pacijent.getId());
            pacijentZaIzmenu.copyValues(pacijent);
            pacijentZaIzmenu=pacRep.save(pacijentZaIzmenu);
            return pacijentZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        pacRep.deleteById(id);
    }
}
