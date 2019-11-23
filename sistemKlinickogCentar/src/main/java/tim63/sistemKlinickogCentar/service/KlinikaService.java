package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.repository.KlinikaRepositoryInterface;

import java.util.Collection;

@Service
public class KlinikaService implements KlinikaInterface {

    @Autowired
    public KlinikaRepositoryInterface repKlinika;

    @Override
    public Collection<Klinika> findAll() {
        return repKlinika.findAll();
    }

    @Override
    public Klinika findById(Long id) {
        return repKlinika.findById(id).orElseGet(null);
    }

    @Override
    public Klinika findByIme(String ime) {
        Klinika u = repKlinika.findByIme(ime);
        return u;
    }

    @Override
    public Klinika create(Klinika klinika) throws Exception {

        Klinika klinika1 = new Klinika();

        // klinika1.setAdminKlinike(klinika.getAdminKlinike());
        klinika1.setAdresa(klinika.getIme());
        klinika1.setIme(klinika.getIme());
        klinika1.setOpis(klinika.getOpis());

        klinika1 = this.repKlinika.save(klinika1);

        return klinika1;
    }

    @Override
    public Klinika update(Klinika klinika) throws Exception {
        Klinika klinikaZaIzmenu = findById(klinika.getId());
        klinikaZaIzmenu.copyValues(klinika);
        return klinikaZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        this.repKlinika.deleteById(id);
    }
}
