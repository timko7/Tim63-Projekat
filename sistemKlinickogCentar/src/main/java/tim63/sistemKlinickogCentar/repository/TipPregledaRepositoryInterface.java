package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim63.sistemKlinickogCentar.model.TipPregleda;

import java.util.Collection;
import java.util.List;

public interface TipPregledaRepositoryInterface extends JpaRepository<TipPregleda, Long> {

    List<TipPregleda> findAll();

    TipPregleda findByNazivTipa(String naziv);

    Collection<TipPregleda> findByIdKlinike(Long idKlinike);
}
