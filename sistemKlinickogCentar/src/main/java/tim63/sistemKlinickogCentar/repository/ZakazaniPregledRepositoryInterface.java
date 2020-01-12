package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;

import java.util.List;


public interface ZakazaniPregledRepositoryInterface extends JpaRepository<ZaktaniPregledi, Long> {

    List<ZaktaniPregledi> findAll();

    List<ZaktaniPregledi> findByIdLekara(Long idLekara);

    List<ZaktaniPregledi> findByIdPacijenta(Long idPcijenta);

}
