package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.repository.PregledOdZahtevaRepository;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class PregledOdZahtevaService implements PregledOdZahtevaServiceInterface {

    @Autowired
    private PregledOdZahtevaRepository pregledOdZahtevaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private KalendarSaleService kalendarSaleService;

    @Autowired
    private PacijentService pacijentService;

    @Autowired
    private LekarService lekarService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Collection<PregledOdZahteva> findAll() {
        return pregledOdZahtevaRepository.findAll();
    }

    @Override
    public Collection<PregledOdZahteva> findByIdKlinike(Long id) {
        return pregledOdZahtevaRepository.findByIdKlinike(id);
    }

    @Override
    public Collection<PregledOdZahteva> findByIdTipa(Long id) {
        return pregledOdZahtevaRepository.findByIdTipa(id);
    }

    @Override
    public Collection<PregledOdZahteva> findByIdLekara(Long id) {
        return pregledOdZahtevaRepository.findByIdLekara(id);
    }

    @Override
    public Collection<PregledOdZahteva> findByIdPacijenta(Long id) {
        return pregledOdZahtevaRepository.findByIdPacijenta(id);
    }

    @Override
    public PregledOdZahteva findById(Long id) {
        return pregledOdZahtevaRepository.findById(id).orElseGet(null);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public PregledOdZahteva update(PregledOdZahteva pregledOdZahteva) {
        PregledOdZahteva zaIzmenu = findById(pregledOdZahteva.getId());
        zaIzmenu.copyValues(pregledOdZahteva);
        zaIzmenu = pregledOdZahtevaRepository.save(zaIzmenu);
        return zaIzmenu;
    }

    @Override
    @Transactional(readOnly = false)
    public PregledOdZahteva create(PregledOdZahteva pregledOdZahteva) throws Exception {

        PregledOdZahteva ret = new PregledOdZahteva();
        Sala salaTemp;

        ret.copyValues(pregledOdZahteva);
        ret = pregledOdZahtevaRepository.save(ret);

        salaTemp = salaService.findById(pregledOdZahteva.getIdSale());
        salaTemp.setSlobodna(false);
        salaService.update(salaTemp);

        kalendarSaleService.createPoPregleduOdZahteva(pregledOdZahteva);

        Pacijent pacijent = pacijentService.findById(pregledOdZahteva.getIdPacijenta());
        Lekar lekar = lekarService.findById(pregledOdZahteva.getIdLekara());

        SimpleMailMessage mailP = new SimpleMailMessage();
        mailP.setTo(pacijent.getEmail());
        //mail.setFrom("isaPSW1@gmail.com");
        mailP.setSubject("Obavestenje za pregled");
        mailP.setText("Pozdrav," + ",\nZa vas zahtev za pregled datuma " + pregledOdZahteva.getDatumVreme() + ", je dodeljena sala: '" + salaTemp.getNaziv() + "'." );
        javaMailSender.send(mailP);

        SimpleMailMessage mailL = new SimpleMailMessage();
        mailL.setTo(lekar.getEmail());
        //mail.setFrom("isaPSW1@gmail.com");
        mailL.setSubject("Obavestenje za pregled");
        mailL.setText("Pozdrav," + ",\nZa vas zahtev za pregled datuma " + pregledOdZahteva.getDatumVreme() + ", je dodeljena sala: '" + salaTemp.getNaziv() + "'." );
        javaMailSender.send(mailL);

        return ret;
    }


    @Override
    public void delete(Long id) {
        pregledOdZahtevaRepository.deleteById(id);
    }


}
