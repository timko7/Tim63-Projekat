package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;

import java.util.List;

public interface ZahtevOdsustvoRepositoryInterface extends JpaRepository<ZahtevOdsustvo, Long> {

    List<ZahtevOdsustvo> findAll();
    List<ZahtevOdsustvo> findByIdKlinike(Long idKlinike);
    List<ZahtevOdsustvo> findByIdLekara(Long idLekara);
}
