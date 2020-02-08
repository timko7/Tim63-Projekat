package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ZakazaniPregledRepositoryInterface extends JpaRepository<ZaktaniPregledi, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    @Query("select zp.id from ZaktaniPregledi zp where zp.datumVreme = :datumVreme")
    ZaktaniPregledi zauzeto(LocalDateTime datumVreme);


    List<ZaktaniPregledi> findAll();

    List<ZaktaniPregledi> findByIdLekara(Long idLekara);

    List<ZaktaniPregledi> findByIdPacijenta(Long idPcijenta);
    List<ZaktaniPregledi> findByIdKlinike(Long idKlinike);

}
