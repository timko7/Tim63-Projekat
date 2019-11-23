package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.Lekar;

import java.util.List;

@Repository
public interface LekarRepositoryInterface extends JpaRepository<Lekar, Long> {

    List<Lekar> findAll();

    Lekar findByEmail(String username);
}
