package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.repository.AdminKlinikeRepositoryInterface;

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
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        AdminKlinike zaObrisati = findByEmail(email);
        adminRepository.deleteById(zaObrisati.getId());
    }
}
