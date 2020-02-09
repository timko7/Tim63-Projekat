package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PregledOdZahtevaRepository extends JpaRepository<PregledOdZahteva, Long> {

    List<PregledOdZahteva> findAll();

    List<PregledOdZahteva> findByIdKlinike(Long idKlinike);

    List<PregledOdZahteva> findByIdTipa(Long idTipa);

    List<PregledOdZahteva> findByIdLekara(Long idLekara);

    List<PregledOdZahteva> findByIdPacijenta(Long idPcijenta);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    @Query("select zp.id from PregledOdZahteva zp where zp.id = :id")
    PregledOdZahteva zauzeto(Long id);

}
