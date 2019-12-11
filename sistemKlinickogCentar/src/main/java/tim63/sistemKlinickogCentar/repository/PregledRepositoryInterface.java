package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.Pregled;

import java.util.List;

public interface PregledRepositoryInterface extends JpaRepository<Pregled, Long> {

    List<Pregled> findAll();

}
