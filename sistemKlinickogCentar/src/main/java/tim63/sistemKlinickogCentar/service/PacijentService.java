package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.repository.PacijentRepositoryImpl;

import java.util.Collection;

public class PacijentService implements PacijentInterface {

    private  PacijentRepositoryImpl pacijentImpl;

    @Override
    public Collection<Pacijent> findAll() {
        Collection<Pacijent> pacijenti= (Collection<Pacijent>) pacijentImpl.findAll();
        return pacijenti;
    }

    @Override
    public Pacijent findOne(Long id) {
        Pacijent p=pacijentImpl.findOne(id);
        return p;
    }

    @Override
    public Pacijent create(Pacijent pacijent) throws Exception {
        if (pacijent.getId() != null) {
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Pacijent p = pacijentImpl.create(pacijent);
        return p;
    }

    @Override
    public Pacijent update(Pacijent pacijent) throws Exception {
        Pacijent pacijentZaIzmenu = findOne(pacijent.getId());
        if (pacijentZaIzmenu == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        pacijentZaIzmenu.setIme(pacijent.getIme());
        pacijentZaIzmenu.setPrezime(pacijent.getPrezime());
        pacijentZaIzmenu.setGrad(pacijent.getGrad());
        pacijentZaIzmenu.setDrzava(pacijent.getDrzava());
        pacijentZaIzmenu.setAdresa(pacijent.getAdresa());
        pacijentZaIzmenu.setTelefon(pacijent.getTelefon());
        pacijentZaIzmenu.setPassword(pacijent.getPassword());
        Pacijent izmenjenPacijent = pacijentImpl.create(pacijentZaIzmenu);
        return izmenjenPacijent;
    }

    @Override
    public void delete(Long id) {
        pacijentImpl.delete(id);
    }
}
