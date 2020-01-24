package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Lekar;

import java.util.Collection;

public interface LekarInterface {

    Collection<Lekar> findAll();

    Lekar findById(Long id);

    Collection<Lekar> findByIdKlinike(Long idKlinike);

    Collection<Lekar> findByIdTipa(Long idTipa);


    Lekar findByEmail(String username);

    Lekar create(Lekar lekar) throws Exception;

    Lekar update(Lekar lekar) throws Exception;

    Lekar dobuniLekara(Lekar lekar, Klinika klinika) throws Exception;//ako radi prebacicemo u admistrator klinike

    Lekar promeniLozinku(Long idLekara, String noviPassword) throws Exception;

    void delete(Long id);
}
