package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;
import tim63.sistemKlinickogCentar.repository.ZahtevOdsustvoRepositoryInterface;

import java.util.Collection;

@Service
public class ZahtevOdsustvoService implements ZahtevOdsustvoServiceInterface {

    @Autowired
    private ZahtevOdsustvoRepositoryInterface repZahtevOdsustvo;

    @Override
    public Collection<ZahtevOdsustvo> findAll() {
        return repZahtevOdsustvo.findAll();
    }

    @Override
    public Collection<ZahtevOdsustvo> findByIdKlinike(Long idKlinike) {
        return repZahtevOdsustvo.findByIdKlinike(idKlinike);
    }

    @Override
    public Collection<ZahtevOdsustvo> findByIdLekara(Long idLekara) {
        return repZahtevOdsustvo.findByIdLekara(idLekara);
    }

    @Override
    public ZahtevOdsustvo findById(Long id) {
        return repZahtevOdsustvo.findById(id).orElseGet(null);
    }

    @Override
    public ZahtevOdsustvo posaljiZahtev(ZahtevOdsustvo zahtevOdsustvo) throws Exception {
        ZahtevOdsustvo ret = new ZahtevOdsustvo();
        zahtevOdsustvo.setObradjen(false);
        zahtevOdsustvo.setPrihvacen(false);
        ret.copyValues(zahtevOdsustvo);
        ret = repZahtevOdsustvo.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {
        repZahtevOdsustvo.deleteById(id);
    }
}
