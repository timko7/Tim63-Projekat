package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SalaService implements SalaSetviceInterface {

    @Autowired
    private SalaRepositoryInterface repositorySala;

    @Autowired
    private KalendarSaleService kalendarSaleService;

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
    public Collection<Sala> pretraziPoNazivuDatumuKlinike(Long idKlinike, String nazivSale, String datum) {
        nazivSale = nazivSale.trim();
        List<Sala> vrati = new ArrayList<>();
        List<Sala> saleKlinike = repositorySala.findByIdKlinike(idKlinike);
        Sala salaNadjena = new Sala();
        boolean isNadjena = false;

        for (Sala sala : saleKlinike) {
            if (sala.getNaziv().equals(nazivSale)) {
                salaNadjena = sala;
                isNadjena = true;
            }
        }

        //Sala sala = findByNaziv(nazivSale);
        LocalDateTime datumZaPregled = LocalDateTime.parse(datum);

        if (!isNadjena) {
            return vrati;
        }

        Collection<KalendarSale> kalendarSale = kalendarSaleService.findByIdSale(salaNadjena.getId());

        if (kalendarSale.isEmpty()) {
            vrati.add(salaNadjena);
            return vrati;
        }

        boolean mozesDodati = false;

        for (KalendarSale kalendarSale1 : kalendarSale) {
            if (datumZaPregled.isAfter(kalendarSale1.getDatumOd()) && datumZaPregled.isBefore(kalendarSale1.getDatumDo())) {
                return vrati;
            } else {
                mozesDodati = true;
            }
        }

        if (mozesDodati) {
            vrati.add(salaNadjena);
        }
        return vrati;
    }

    @Override
    public SalaDatumDTO getPrviSledeciSlobodanTermin(Long idKlinike, String datum) {

        Collection<Sala> saleKlinike = findByIdKlinike(idKlinike);
        Sala salaNadjena = new Sala();
        LocalDateTime datumZaNaci = LocalDateTime.parse(datum);
        datumZaNaci = datumZaNaci.plusMinutes(1);
        Collection<KalendarSale> kalendarSaleSve = kalendarSaleService.findAll();


        boolean nasoSalu = false;
        boolean kraj = false;

        while (!kraj) {
            for (Sala sala : saleKlinike) {
                Collection<KalendarSale> kalendarSale = kalendarSaleService.findByIdSale(sala.getId());
                for (KalendarSale zauzeceSale1 : kalendarSale) {
                    if (datumZaNaci.isAfter(zauzeceSale1.getDatumOd()) && datumZaNaci.isBefore(zauzeceSale1.getDatumDo())) {
                        //System.out.println("Pronasao izmedju!!");
                        nasoSalu = false;
                        break;
                    } else {
                        //System.out.println("NIje izmedju!!");
                        nasoSalu = true;
                    }
                }
                if (nasoSalu) {
                    salaNadjena = sala;
                    kraj = true;
                    break;
                }
            }
            if (!kraj) {
                datumZaNaci = datumZaNaci.plusMinutes(5);
            }
        }

        SalaDatumDTO ret = new SalaDatumDTO();
        ret.setDatum(datumZaNaci);
        ret.setSala(salaNadjena);

        return ret;
    }

    @Override
    public void deleteByNaziv(String naziv) {
        Sala salaZaObrisati = findByNaziv(naziv);
        repositorySala.deleteById(salaZaObrisati.getId());
    }


}
