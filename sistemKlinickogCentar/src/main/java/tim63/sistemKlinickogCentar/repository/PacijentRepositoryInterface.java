package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.Pacijent;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacijentRepositoryInterface extends JpaRepository<Pacijent, Long> {

   List<Pacijent> findAll();

    Pacijent  findByEmail( String username );

   // Pacijent update(Pacijent pacijent) throws Exception;

    //void delete(Long id);
}





