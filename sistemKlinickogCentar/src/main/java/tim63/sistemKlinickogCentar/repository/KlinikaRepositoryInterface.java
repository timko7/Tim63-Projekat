package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.Klinika;

import java.util.List;

@Repository
public interface KlinikaRepositoryInterface extends JpaRepository<Klinika, Long> {

    List<Klinika> findAll();

    Klinika findByIme(String ime);
}
