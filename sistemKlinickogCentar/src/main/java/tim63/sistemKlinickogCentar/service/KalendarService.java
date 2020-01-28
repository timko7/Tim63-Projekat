package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.repository.KalendarRepository;
import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class KalendarService implements KalendarServiceInterface{

    @Autowired
    private KalendarRepository repositoryKalendar;

    @Override
    public Collection<Kalendar> findAll() {
        return repositoryKalendar.findAll();
    }

    @Override
    public List<Kalendar> findByIdLekara(Long id) {
        List<Kalendar> kalendari=new ArrayList<>();
        kalendari=repositoryKalendar.findByIdLekara(id);
        if(kalendari!=null)
            return kalendari;
        else
        return null;
    }

   /* @Override
    public List<Kalendar> findByIdPacijenta(Long id) {
        return repositoryKalendar.findByIdPcijenta(id);
    }*/

    @Override
    public Kalendar create(Pregled pregled) throws Exception {
        Kalendar ret = new Kalendar();
        ret.setDatum(pregled.getDatumVreme());
        ret.setOd(pregled.getDatumVreme());
        ret.setDokle(pregled.getDatumVreme().plusMinutes(pregled.getTrajanjePregleda()));
        ret.setIdLekara(pregled.getIdLekara());
      //  ret.setIdPacijenta(pregled.getIdPacijenta());
        ret = repositoryKalendar.save(ret);
        return ret;
    }

   @Override
    public Kalendar createPrekoPesimistickih(ZaktaniPregledi pregled) throws Exception {
        Kalendar ret = new Kalendar();
        ret.setDatum(pregled.getDatumVreme());
        ret.setOd(pregled.getDatumVreme());
        ret.setDokle(pregled.getDatumVreme().plusMinutes(pregled.getTrajanjePregleda()));
        ret.setIdLekara(pregled.getIdLekara());
     //   ret.setIdPacijenta(pregled.getIdPacijenta());
        ret = repositoryKalendar.save(ret);
        return ret;
    }

    @Override
    public void delete(Long id) {
            repositoryKalendar.deleteById(id);
        }
    }

