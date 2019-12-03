package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Lekar;

import java.util.Collection;

public interface LekarInterface {

    Collection<Lekar> findAll();

    Lekar findById(Long id);

    Lekar findByEmail(String username);

    Lekar create(Lekar lekar) throws Exception;

    Lekar update(Lekar lekar) throws Exception;

    Lekar dobuniLekara(Lekar lekar, Klinika klinika) throws Exception;//ako radi prebacicemo u admistrator klinike

    void delete(Long id);
}