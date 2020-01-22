package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;

import java.util.Collection;

public interface ZahtevOdsustvoServiceInterface {

    Collection<ZahtevOdsustvo> findAll();

    Collection<ZahtevOdsustvo> findByIdKlinike(Long idKlinike);

    Collection<ZahtevOdsustvo> findByIdLekara(Long idLekara);

    ZahtevOdsustvo findById(Long id);

    ZahtevOdsustvo posaljiZahtev(ZahtevOdsustvo zahtevOdsustvo) throws Exception;

    void delete(Long id);

}
