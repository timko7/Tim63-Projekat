package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.repository.AdminKlinikeRepositoryInterface;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AdminKlinikeService implements AdminKlinikeServiceInterface {

    @Autowired
    private AdminKlinikeRepositoryInterface adminRepository;

    @Override
    public Collection<AdminKlinike> findAll() {
        Collection<AdminKlinike> result = adminRepository.findAll();
        return result;
    }

    @Override
    public ArrayList<AdminKlinike> findByIdKlinike(Long idKlinike) {
        return adminRepository.findByIdKlinike(idKlinike);
    }

    @Override
    public AdminKlinike findById(Long id) {
        AdminKlinike ret = adminRepository.findById(id).orElseGet(null);
        return ret;
    }

    @Override
    public AdminKlinike findByEmail(String email) {
        AdminKlinike ret = adminRepository.findByEmail(email);
        return ret;
    }

    @Override
    public AdminKlinike create(AdminKlinike adminKlinike) throws Exception {
        AdminKlinike ret = new AdminKlinike();

        adminKlinike.setPrviPutLogovan(true);
        ret.copyValues(adminKlinike);
        ret = adminRepository.save(ret);
        return ret;
    }

    @Override
    public AdminKlinike update(AdminKlinike adminKlinike) throws Exception {
        AdminKlinike adminKlinikeZaIzmenu = findById(adminKlinike.getId());
        adminKlinikeZaIzmenu.copyValues(adminKlinike);
        adminKlinikeZaIzmenu = adminRepository.save(adminKlinikeZaIzmenu);
        return adminKlinikeZaIzmenu;
    }

    @Override
    public AdminKlinike promeniLozinku(Long idAdmina, String noviPassword) throws Exception {
        AdminKlinike adminZaPromenu = findById(idAdmina);
        adminZaPromenu.setPassword(noviPassword);
        adminZaPromenu.setPrviPutLogovan(false);
        adminZaPromenu = adminRepository.save(adminZaPromenu);
        return adminZaPromenu;
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        AdminKlinike zaObrisati = findByEmail(email);
        adminRepository.deleteById(zaObrisati.getId());
    }
}
