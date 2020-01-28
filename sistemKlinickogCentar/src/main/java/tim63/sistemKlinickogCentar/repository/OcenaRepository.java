package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.Ocena;
import tim63.sistemKlinickogCentar.model.Pregled;

import java.util.List;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {
    List<Ocena> findAll();
    List<Ocena> findByIdLekara(Long idLekara);

}

