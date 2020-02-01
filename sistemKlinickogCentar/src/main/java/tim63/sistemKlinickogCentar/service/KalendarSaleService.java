package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.repository.KalendarSaleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class KalendarSaleService implements KalendarSaleServiceInterface {

    @Autowired
    private KalendarSaleRepository kalendarSaleRepository;

    @Override
    public Collection<KalendarSale> findAll() {
        return kalendarSaleRepository.findAll();
    }

    @Override
    public Collection<KalendarSale> findByIdSale(Long id) {
        List<KalendarSale> kalendarSalePoId = new ArrayList<KalendarSale>();
        kalendarSalePoId = kalendarSaleRepository.findByIdSale(id);
        return  kalendarSalePoId;
    }

    @Override
    public KalendarSale createPoPredefinisanomPregledu(Pregled pregled) {
        KalendarSale ret = new KalendarSale();
        ret.setIdSale(pregled.getIdSale());
        ret.setDatumOd(pregled.getDatumVreme());
        ret.setDatumDo(pregled.getDatumVreme().plusMinutes(pregled.getTrajanjePregleda()));
        ret = kalendarSaleRepository.save(ret);
        return ret;
    }

    @Override
    public KalendarSale createPoPregleduOdZahteva(PregledOdZahteva pregledOdZahteva) {
        KalendarSale ret = new KalendarSale();
        ret.setIdSale(pregledOdZahteva.getIdSale());
        ret.setDatumOd(pregledOdZahteva.getDatumVreme());
        ret.setDatumDo(pregledOdZahteva.getDatumVreme().plusMinutes(pregledOdZahteva.getTrajanjePregleda()));
        ret = kalendarSaleRepository.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {
        kalendarSaleRepository.deleteById(id);
    }
}
