package tim63.sistemKlinickogCentar.service;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.*;

import tim63.sistemKlinickogCentar.repository.PacijentRepositoryInterface;

import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class PregledService implements PregledServiceInterface {

    @Autowired
    private PregledRepositoryInterface repositoryPregled;

    @Autowired
    private SalaService salaService;

    @Autowired
    private TipPregledaService tipPregledaService;

    @Autowired
    private LekarService lekarService;

    @Autowired
    private PacijentRepositoryInterface pacijentRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Collection<Pregled> findAll() {
        return repositoryPregled.findAll();
    }

    @Override
    public Collection<Pregled> findByIdKlinike(Long id) {
        return repositoryPregled.findByIdKlinike(id);
    }

    @Override
    public Collection<Pregled> findByIdTipa(Long id) {
        return repositoryPregled.findByIdTipa(id);
    }

    @Override
    public Collection<Pregled> findByIdLekara(Long id) {
        return repositoryPregled.findByIdLekara(id);
    }

    @Override
    public Collection<Pregled> findByIdPacijenta(Long id) {
        return repositoryPregled.findByIdPacijenta(id);
    }

    @Override
    public Pregled findById(Long id) {
        return repositoryPregled.findById(id).orElseGet(null);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Pregled update(Pregled pregled) throws Exception {
        Pacijent p=new Pacijent();
        Pregled zaIzmenu = findById(pregled.getId());
        zaIzmenu.copyValuesZaRezervaciju(pregled);
        if(!pregled.getVerzija().equals(zaIzmenu.getVerzija())){
            throw new ObjectOptimisticLockingFailureException("Neko pre vas je vec menjao",true);
        }
        zaIzmenu = repositoryPregled.save(zaIzmenu);
        p=pacijentRepository.findById(zaIzmenu.getIdPacijenta()).orElseGet(null);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(p.getEmail());
        //mail.setFrom("isaPSW1@gmail.com");
        mail.setSubject("Rezervisanje predefinisanih pregleda");
        mail.setText("Pozdrav" + ",\nUspesno ste rezervisali pregled datuma:"+ zaIzmenu.getDatumVreme());
        javaMailSender.send(mail);
        //Transport.send(mail);
        //posaljiMejl(lekar);

        return zaIzmenu;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public Pregled create(Pregled pregled) throws Exception {
        Pregled ret = new Pregled();
        Sala salaTemp = new Sala();
        TipPregleda tipTemp;

        int trajanje = pregled.getTrajanjePregleda();
        LocalDateTime datumVreme = pregled.getDatumVreme();
        double cena = pregled.getCena();

        if (trajanje < 1) {
            return  null;
        }

        if (cena < 0) {
            return null;
        }

        LocalDateTime datumVremeSada = LocalDateTime.now();

        int compareValue = pregled.getDatumVreme().compareTo(datumVremeSada);

        if (compareValue < 0) {
            return null;
        }
        if(pregled.getIdSale()==null || pregled.getIdLekara()==null || pregled.getIdKlinike()==null || pregled.getIdTipa()==null){
            return null;
        }

        salaTemp = salaService.findById(pregled.getIdSale());
        salaTemp.setSlobodna(false);
        salaService.update(salaTemp);

        tipTemp = tipPregledaService.findById(pregled.getIdTipa());
        tipTemp.setSlobodan(false);
        tipPregledaService.update(tipTemp);

        Lekar lekarTmp = lekarService.findById(pregled.getIdLekara());
        lekarTmp.setSlobodan(false);
        lekarService.update(lekarTmp);

        ret.copyValues(pregled);
        ret = repositoryPregled.save(ret);
        return ret;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        repositoryPregled.deleteById(id);
    }
}
