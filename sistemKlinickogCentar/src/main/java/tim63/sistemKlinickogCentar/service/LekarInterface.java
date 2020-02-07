package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.PretragaKlinike;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface LekarInterface {

    Collection<Lekar> findAll();

    Lekar findById(Long id);

    Collection<Lekar> findByIdKlinike(Long idKlinike);

    Collection<Lekar> findByIdTipa(Long idTipa);

    ArrayList<Lekar> pretrazi(PretragaKlinike zahtev);
    ArrayList<Lekar> pretraziLekre(PretragaKlinike zahtev);
    ArrayList<Integer> vratiTermineNaFront(Long idLekara, LocalDate datum);
    ArrayList<Integer> vratiTermine(Long idLekara);

    Lekar findByEmail(String username);

    Lekar create(Lekar lekar) throws Exception;

    Lekar update(Lekar lekar) throws Exception;

   // Lekar dobuniLekara(Lekar lekar, Klinika klinika) throws Exception;//ako radi prebacicemo u admistrator klinike

    Lekar promeniLozinku(Long idLekara, String noviPassword) throws Exception;

    void delete(Long id);
}
