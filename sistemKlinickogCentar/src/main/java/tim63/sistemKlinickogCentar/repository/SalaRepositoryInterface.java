package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.Sala;

import java.util.List;

public interface SalaRepositoryInterface extends JpaRepository<Sala, Long> {
    List<Sala> findAll();
    Sala findByNaziv(String naziv);
}

