package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.repository.KlinikaRepositoryInterface;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class KlinikaService implements KlinikaInterface {

    @Autowired
    public KlinikaRepositoryInterface repKlinika;

    @Autowired
    private PregledService pregledService;

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;

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
        klinikaZaIzmenu=repKlinika.save(klinikaZaIzmenu);
        return klinikaZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        this.repKlinika.deleteById(id);
    }

    @Override
    public Klinika izmeniPodatke(String stariNazivKlinike, Klinika klinika) {
        Klinika klinikaZaIzmenu = findById(klinika.getId());
        Collection<Klinika> sveKlinike = findAll();

        if (!klinika.getIme().equals(stariNazivKlinike)) {
            for (Klinika klinika1 : sveKlinike) {
                if (klinika.getIme().equals(klinika1.getIme())) {
                    return null;
                }
            }
        }

        klinikaZaIzmenu.copyValues(klinika);
        klinikaZaIzmenu = repKlinika.save(klinika);
        return klinikaZaIzmenu;
    }

    @Override
    public double getPrihodPoPeriod(Long idKlinike, String pocetak, String kraj) {

        Collection<Pregled> predefinisaniPregledi = pregledService.findByIdKlinike(idKlinike);
        Collection<PregledOdZahteva> preglediOdZahteva = pregledOdZahtevaService.findByIdKlinike(idKlinike);

        LocalDateTime datePocetak = LocalDateTime.parse(pocetak);
        LocalDateTime dateKraj = LocalDateTime.parse(kraj);

        double ukupanPrihod = 0.0;

        for (Pregled pregled : predefinisaniPregledi) {
            if (pregled.getDatumVreme().isAfter(datePocetak) && pregled.getDatumVreme().isBefore(dateKraj)) {
                if (pregled.isOdradjen()) {
                    ukupanPrihod += pregled.getCena();
                }
            }
        }

        for (PregledOdZahteva pregledOdZahteva : preglediOdZahteva) {
            if (pregledOdZahteva.getDatumVreme().isAfter(datePocetak) && pregledOdZahteva.getDatumVreme().isBefore(dateKraj)) {
                if (pregledOdZahteva.isOdradjen()) {
                    ukupanPrihod += pregledOdZahteva.getCena();
                }
            }
        }

        return ukupanPrihod;
    }
}
