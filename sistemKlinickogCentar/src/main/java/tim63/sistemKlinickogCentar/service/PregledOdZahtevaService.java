package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.*;
import tim63.sistemKlinickogCentar.repository.PregledOdZahtevaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class PregledOdZahtevaService implements PregledOdZahtevaServiceInterface {

    @Autowired
    private PregledOdZahtevaRepository pregledOdZahtevaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private KalendarService kalendarService;

    @Autowired
    private KalendarSaleService kalendarSaleService;

    @Autowired
    private PacijentService pacijentService;

    @Autowired
    private LekarService lekarService;

    @Autowired
    private TipPregledaService tipPregledaService;

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
    public PregledOdZahteva pregledaj(PregledOdZahteva pregledOdZahteva) throws Exception {
        PregledOdZahteva zaIzmenu = findById(pregledOdZahteva.getId());
        //zaIzmenu.copyValues(pregledOdZahteva);
        zaIzmenu.setOdradjen(true);
        zaIzmenu.setTrajanjePregleda(pregledOdZahteva.getTrajanjePregleda());
        zaIzmenu = pregledOdZahtevaRepository.save(zaIzmenu);
        return zaIzmenu;
    }


    @Override
    @Transactional(readOnly = false)
    public PregledOdZahteva create(PregledOdZahteva pregledOdZahteva) throws Exception {

        PregledOdZahteva ret = new PregledOdZahteva();
        Sala salaTemp;
        TipPregleda tipTemp = tipPregledaService.findById(pregledOdZahteva.getIdTipa());;

        pregledOdZahteva.setCena(tipTemp.getCena());
        ret.copyValues(pregledOdZahteva);
        ret = pregledOdZahtevaRepository.save(ret);

        salaTemp = salaService.findById(pregledOdZahteva.getIdSale());
        salaTemp.setSlobodna(false);
        salaService.update(salaTemp);

        tipTemp.setSlobodan(false);
        tipPregledaService.update(tipTemp);

        kalendarSaleService.createPoPregleduOdZahteva(pregledOdZahteva);

        Pacijent pacijent = pacijentService.findById(pregledOdZahteva.getIdPacijenta());
        Lekar lekar = lekarService.findById(pregledOdZahteva.getIdLekara());

        lekar.setSlobodan(false);
        lekarService.update(lekar);

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
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public boolean okaziPregled(Long id) {
        LocalDateTime datumVremeSada=LocalDateTime.now();
        PregledOdZahteva zaIzmenu=this.findById(id);

        if(!datumVremeSada.isBefore(zaIzmenu.getDatumVreme())){
           return false;
        }


        Collection<Kalendar>kalendari=kalendarService.findAll();
        for(Kalendar k:kalendari){
            if(k.getDatum().isEqual(zaIzmenu.getDatumVreme())){
                kalendarService.delete(k.getId());
            }
        }
        Collection<KalendarSale>kalendariSale=kalendarSaleService.findAll();
        for(KalendarSale k:kalendariSale){
            if(k.getDatumOd().isEqual(zaIzmenu.getDatumVreme())){
                kalendarSaleService.delete(k.getId());
            }
        }
        this.delete(id);

        return true;
    }


    @Override
    public void delete(Long id) {
        pregledOdZahtevaRepository.deleteById(id);
    }


}
