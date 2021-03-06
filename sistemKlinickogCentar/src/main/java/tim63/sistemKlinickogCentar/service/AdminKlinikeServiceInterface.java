package tim63.sistemKlinickogCentar.service;

import tim63.sistemKlinickogCentar.model.AdminKlinike;

import java.util.ArrayList;
import java.util.Collection;

public interface AdminKlinikeServiceInterface {

    Collection<AdminKlinike> findAll();

    ArrayList<AdminKlinike> findByIdKlinike(Long idKlinike);
    AdminKlinike findById(Long id);

    AdminKlinike findByEmail(String email);

    AdminKlinike create(AdminKlinike adminKlinike) throws Exception;

    AdminKlinike update(AdminKlinike adminKlinike) throws Exception;

    public  AdminKlinike promeniLozinku(Long idAdmina, String noviPassword) throws Exception;

    void delete(Long id);

    void deleteByEmail(String email);

}
