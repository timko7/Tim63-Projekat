package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.AdminKlinike;

import java.util.ArrayList;
import java.util.List;

public interface AdminKlinikeRepositoryInterface extends JpaRepository<AdminKlinike, Long> {

    List<AdminKlinike> findAll();

    AdminKlinike findByEmail(String email);
    ArrayList<AdminKlinike> findByIdKlinike(Long idKlinike);

}

