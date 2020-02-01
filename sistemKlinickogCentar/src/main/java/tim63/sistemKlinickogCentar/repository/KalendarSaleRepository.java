package tim63.sistemKlinickogCentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim63.sistemKlinickogCentar.model.KalendarSale;

import java.util.List;

@Repository
public interface KalendarSaleRepository extends JpaRepository<KalendarSale, Long> {

    List<KalendarSale> findAll();
    List<KalendarSale> findByIdSale(Long id);
}
