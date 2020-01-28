package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.Kalendar;

import java.util.Collection;
import java.util.List;

@Repository
public interface KalendarRepository  extends JpaRepository<Kalendar, Long> {

    List<Kalendar> findAll();
    List<Kalendar> findByIdLekara(Long id);
  //  List<Kalendar> findByIdPcijenta(Long id);

}

