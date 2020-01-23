package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;

public interface ZahtevOdsustvoServiceInterface {

    Collection<ZahtevOdsustvo> findAll();

    Collection<ZahtevOdsustvo> findByIdKlinike(Long idKlinike);

    Collection<ZahtevOdsustvo> findByIdLekara(Long idLekara);

    ZahtevOdsustvo findById(Long id);

    ZahtevOdsustvo posaljiZahtev(ZahtevOdsustvo zahtevOdsustvo) throws Exception;

    void delete(Long id);

    ZahtevOdsustvo odobriZahtev(ZahtevOdsustvo zahtevOdsustvo) throws IOException, MessagingException;

    ZahtevOdsustvo odbiZahtev(ZahtevOdsustvo zahtevOdsustvo, String razlogOdbijanja) ;

}
