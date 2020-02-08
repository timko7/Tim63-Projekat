package tim63.sistemKlinickogCentar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.repository.ZakazaniPregledRepositoryInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ZakazaniPregledService implements ZakazaniPreglediInterface {
    @Autowired
    private ZakazaniPregledRepositoryInterface zpi;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalaService salaService;

    @Autowired
    private AdminKlinikeService adminKlinikeService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;

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
    public Collection<ZaktaniPregledi> findByIdKlinike(Long id) {
        return zpi.findByIdKlinike(id);
    }

    @Override
    public ZaktaniPregledi findById(Long id)  {
        return zpi.findById(id).orElseGet(null);
    }


   /* @Override
    public ZaktaniPregledi update(ZaktaniPregledi pacijent) throws Exception {
        ZaktaniPregledi zaIzmenu = findById(pacijent.getId());
        zaIzmenu.copyValues(pacijent);
        zaIzmenu = zpi.save(zaIzmenu);
        return zaIzmenu;
    }*/

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public ZaktaniPregledi odradi(Long id) {
        ZaktaniPregledi zaOdradu = findById(id);
        zaOdradu.setOdradjen(true);
        zaOdradu = zpi.save(zaOdradu);
        return zaOdradu;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public ZaktaniPregledi create(ZaktaniPregledi pregled)  {
        ZaktaniPregledi ret = new ZaktaniPregledi();

        System.out.println(pregled);
       // int trajanje = pregled.getTrajanjePregleda();
        LocalDateTime datumVreme = pregled.getDatumVreme();
        ArrayList<AdminKlinike>admini=new ArrayList<>();

        double cena = pregled.getCena();


       /*if (trajanje < 1) {
            return null;
        }
*/
        if (cena < 0) {
            return null;
        }
        if(pregled.getIdKlinike()==null || pregled.getIdLekara()==null || pregled.getIdPacijenta()==null || pregled.getIdTipa()==null){
            return null;
        }

        if(pregled.getDatumVreme()==null){
            return null;
        }
        LocalDateTime datumVremeSada = LocalDateTime.now();

        int compareValue = pregled.getDatumVreme().compareTo(datumVremeSada);

        if (compareValue < 0) {
            return null;
        }
        ret.setDatumVreme(pregled.getDatumVreme());
       ret.setCena(pregled.getCena());
      // ret.setTrajanjePregleda(pregled.getTrajanjePregleda());
        ret.setIdKlinike(pregled.getIdKlinike());
        ret.setIdLekara(pregled.getIdLekara());
        ret.setIdTipa(pregled.getIdTipa());
        ret.setIdPacijenta(pregled.getIdPacijenta());
      //  ret.setIdSale(pregled.getIdSale());
        ret.setRezervisan(true);

        try{
            ZaktaniPregledi zp=zpi.zauzeto(datumVreme);
            if(zp!=null) throw new RuntimeException("Zahtev vec kreiran");
            else
                ret = this.zpi.save(ret);
        }catch (Exception e){
            throw new RuntimeException("Zahtev je vec kreiran u trazenom terminu,osvezite stranicu");
        }


        System.out.println(ret);
        admini=adminKlinikeService.findByIdKlinike(ret.getIdKlinike());

        for(AdminKlinike a:admini) {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(a.getEmail());
            //mail.setFrom("isaPSW1@gmail.com");
            mail.setSubject("Zahtev za pregledom/rezervacijom");
            mail.setText("Pozdrav" + ",\nZeleo bih da zakazem pregled u Vasoj klinici:" + ret.getDatumVreme());
            javaMailSender.send(mail);
        }
        return ret;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {

        zpi.deleteById(id);
    }


    /*
     * Logika se izvrsava sa razmakom izmedju kraja poslednjeg izvrsavanja i pocetka sledeceg.
     *
     * 'fixedDelay' se koristi kao indikacija vremena koje treba da prodje izmedju izvrsavanja.
     * 'initialDelay' se koristi kao indikacija koliko da se saceka posle startovanja aplikacije sa prvim izvrsavanjem metode.
     */
    @Transactional(readOnly = false)
    @Scheduled(initialDelayString = "3000", fixedDelayString = "30000")
    public void automatskiNapraviPregledeOdZahteva() throws Exception {
        logger.info("> automatskiNapraviPregledeOdZahteva()");

        LocalDateTime trenutnoVreme = LocalDateTime.now();
        if (trenutnoVreme.getHour() > 22 && trenutnoVreme.getMinute() > 54) {
            System.out.println(trenutnoVreme.toString());

            Collection<ZaktaniPregledi> sviZahtevi =  findAll();
            for (ZaktaniPregledi zahtevZaPregled : sviZahtevi) {
                if (!zahtevZaPregled.isOdradjen()) {
                    PregledOdZahteva newPregled = new PregledOdZahteva();
                    newPregled.setCena(zahtevZaPregled.getCena());
                    newPregled.setIdKlinike(zahtevZaPregled.getIdKlinike());
                    newPregled.setIdLekara(zahtevZaPregled.getIdLekara());
                    newPregled.setIdPacijenta(zahtevZaPregled.getIdPacijenta());
                    newPregled.setIdTipa(zahtevZaPregled.getIdTipa());
                    newPregled.setOdradjen(false);
                    newPregled.setTrajanjePregleda(zahtevZaPregled.getTrajanjePregleda());

                    SalaDatumDTO salaIDatum = salaService.getPrviSledeciSlobodanTermin(zahtevZaPregled.getIdKlinike(), zahtevZaPregled.getDatumVreme().toString());

                    newPregled.setIdSale(salaIDatum.getSala().getId());
                    newPregled.setDatumVreme(salaIDatum.getDatum());

                    pregledOdZahtevaService.create(newPregled);
                    odradi(zahtevZaPregled.getId());
                }
            }
        }

        logger.info("< automatskiNapraviPregledeOdZahteva()");
    }

}

