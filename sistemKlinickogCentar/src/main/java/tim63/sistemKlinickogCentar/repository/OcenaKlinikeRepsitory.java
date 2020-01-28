package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.OcenaKlinike;

import java.util.List;

public interface OcenaKlinikeRepsitory extends JpaRepository<OcenaKlinike, Long> {
    List<OcenaKlinike> findAll();
    List<OcenaKlinike> findByIdKlinike(Long idKlinike);
}
