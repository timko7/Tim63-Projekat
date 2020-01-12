package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.repository.ZakazaniPregledRepositoryInterface;

import java.util.Collection;

@Service
public class ZakazaniPregledService implements ZakazaniPreglediInterface {
    @Autowired
    private ZakazaniPregledRepositoryInterface zpi;


    @Override
    public Collection<ZaktaniPregledi> findAll() {
        return zpi.findAll();
    }

    @Override
    public Collection<ZaktaniPregledi> findByIdLekara(Long id) {
        return zpi.findByIdLekara(id);
    }

    @Override
    public Collection<ZaktaniPregledi> findByIdPacijenta(Long id) {
        return zpi.findByIdPacijenta(id);
    }

    @Override
    public ZaktaniPregledi findById(Long id)  {
        return zpi.findById(id).orElseGet(null);
    }

    @Override
    public ZaktaniPregledi update(ZaktaniPregledi pacijent) throws Exception {
        ZaktaniPregledi zaIzmenu = findById(pacijent.getId());
        zaIzmenu.copyValues(pacijent);
        zaIzmenu = zpi.save(zaIzmenu);
        return zaIzmenu;
    }

    @Override
    public ZaktaniPregledi create(ZaktaniPregledi pregled) throws Exception {
        ZaktaniPregledi ret = new ZaktaniPregledi();

        ret.setDatumVreme(pregled.getDatumVreme());
       ret.setCena(pregled.getCena());
       ret.setTrajanjePregleda(pregled.getTrajanjePregleda());
        ret.setIdKlinike(pregled.getIdKlinike());
        ret.setIdLekara(pregled.getIdLekara());
        ret.setIdTipa(pregled.getIdTipa());
        ret.setIdPacijenta(pregled.getIdPacijenta());
        ret.setIdSale(pregled.getIdSale());

        ret = this.zpi.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {

        zpi.deleteById(id);
    }
}
