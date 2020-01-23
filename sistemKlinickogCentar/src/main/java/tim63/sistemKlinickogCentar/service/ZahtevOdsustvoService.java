package tim63.sistemKlinickogCentar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;
import tim63.sistemKlinickogCentar.repository.LekarRepositoryInterface;
import tim63.sistemKlinickogCentar.repository.ZahtevOdsustvoRepositoryInterface;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class ZahtevOdsustvoService implements ZahtevOdsustvoServiceInterface {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ZahtevOdsustvoRepositoryInterface repZahtevOdsustvo;

    @Autowired
    private LekarRepositoryInterface repLekar;

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

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public ZahtevOdsustvo odobriZahtev(ZahtevOdsustvo zahtevOdsustvo) throws IOException, MessagingException {

        Lekar lekar = repLekar.findById(zahtevOdsustvo.getIdLekara()).orElse(null);

        logger.info("> odobrenje zahteva id:{}", zahtevOdsustvo.getId());
        ZahtevOdsustvo zahtevZaOdobriti = findById(zahtevOdsustvo.getId());
        zahtevZaOdobriti.setPrihvacen(true);
        zahtevZaOdobriti.setObradjen(true);
        logger.info("pre save metode:: " + zahtevOdsustvo.toString());
        repZahtevOdsustvo.save(zahtevZaOdobriti);
        logger.info(zahtevOdsustvo.toString());
        logger.info("< odobrenje zahteva id:{}", zahtevOdsustvo.getId());

        logger.info("Slanje mejlaa!!!");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(lekar.getEmail());
        //mail.setFrom("isaPSW1@gmail.com");
        mail.setSubject("Odgovor za odsustvo");
        mail.setText("Pozdrav tamo" + ",\nVas zahtev za odsustvo je odobren!");
        javaMailSender.send(mail);
        //Transport.send(mail);
        //posaljiMejl(lekar);

        System.out.println("Email poslat!");


        return zahtevZaOdobriti;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public ZahtevOdsustvo odbiZahtev(ZahtevOdsustvo zahtevOdsustvo, String razlogOdbijanja) {

        Lekar lekar = repLekar.findById(zahtevOdsustvo.getIdLekara()).orElse(null);

        logger.info("> odbijanje zahteva id:{}", zahtevOdsustvo.getId());
        ZahtevOdsustvo zahtevZaOdbiti = findById(zahtevOdsustvo.getId());
        zahtevZaOdbiti.setPrihvacen(false);
        zahtevZaOdbiti.setObradjen(true);
        logger.info("pre save metode:: " + zahtevOdsustvo.toString());
        repZahtevOdsustvo.save(zahtevZaOdbiti);
        logger.info(zahtevOdsustvo.toString());
        logger.info("< odbijanje zahteva id:{}", zahtevOdsustvo.getId());

        logger.info("Slanje mejlaa!!!");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(lekar.getEmail());
        //mail.setFrom("isaPSW1@gmail.com");
        mail.setSubject("Odgovor za odsustvo");
        mail.setText("Pozdrav tamo" + ",\nVas zahtev za odsustvo je odbijen" + "!\nRazlog: '" + razlogOdbijanja + "'");
        javaMailSender.send(mail);
        //Transport.send(mail);
        //posaljiMejl(lekar);

        System.out.println("Email poslat!");

        return zahtevZaOdbiti;
    }

}
