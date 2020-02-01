package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;

import java.util.List;

@Repository
public interface PregledOdZahtevaRepository extends JpaRepository<PregledOdZahteva, Long> {

    List<PregledOdZahteva> findAll();

    List<PregledOdZahteva> findByIdKlinike(Long idKlinike);

    List<PregledOdZahteva> findByIdTipa(Long idTipa);

    List<PregledOdZahteva> findByIdLekara(Long idLekara);

    List<PregledOdZahteva> findByIdPacijenta(Long idPcijenta);

}
