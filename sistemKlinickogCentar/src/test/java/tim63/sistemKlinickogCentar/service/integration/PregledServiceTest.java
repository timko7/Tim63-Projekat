package tim63.sistemKlinickogCentar.service.integration;

import org.hibernate.cache.spi.entry.StructuredCacheEntry;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;
import tim63.sistemKlinickogCentar.service.PregledService;
import tim63.sistemKlinickogCentar.service.SalaService;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PregledServiceTest {



    @Autowired
    private PregledService pregledService;

    @Autowired
    private PregledRepositoryInterface repositoryPregled;

    @Autowired
    private SalaService salaService;

    @BeforeEach
    void setUp()   {


    }

    @Test
    void findAll() {
        List<Pregled> pregledi = (List<Pregled>) pregledService.findAll();
        assertEquals(pregledi.size(), 5);
    }

    @Test
    void findByIdKlinike() {
        Collection<Pregled> pregledi= pregledService.findByIdKlinike(1L);

        assertEquals(pregledi.size(), 3);
    }

    @Test
    void findByIdTipa() {

        Collection<Pregled> pregledi= pregledService.findByIdTipa(1L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void findByIdLekara() {
        Collection<Pregled> pregledi= pregledService.findByIdLekara(2L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void findByIdPacijenta() {
        Collection<Pregled> pregledi= pregledService.findByIdPacijenta(2L);

        assertEquals(pregledi.size(), 1);
    }

    @Test
    void findById() {

        Pregled pregled = pregledService.findById(2L);

        String datumUstringu="2020-03-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        assertEquals(pregled.getDatumVreme(),datum);
        assertEquals(pregled.getTrajanjePregleda(),90);
        assertEquals(pregled.getIdSale(),1L);
        assertEquals(pregled.getCena(),700);
        assertEquals(pregled.getIdLekara(),1L);
        assertEquals(pregled.getIdKlinike(),1L);
        assertEquals(pregled.getIdPacijenta(),null);
        assertEquals(pregled.getIdTipa(),1L);
        assertEquals(pregled.isOdradjen(),false);
        assertEquals(pregled.isRezervisan(),false);
    }



    @Test
    @Transactional
    @Rollback(true)
    void update() throws Exception {
        Pregled pregled1 = pregledService.findById(2L);
       // Pregled pregled2 = pregledService.findById(1L);

        //modifikovanje istog objekta
        pregled1.setIdPacijenta(2L);
        pregled1.setRezervisan(true);



        //verzija oba objekta je 0
        assertEquals(0, pregled1.getVerzija().intValue());
       // assertEquals(0, pregled2.getVerzija().intValue());

        //pokusaj cuvanja prvog objekta
        repositoryPregled.save(pregled1);

    }

    @Transactional
    @Rollback
    @Test
    void create() {
        Pregled pregled = new Pregled();

        String datumUstringu="2020-03-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(null);
        pregled.setIdSale(1L);
        pregled.setIdTipa(1L);
        pregled.setOdradjen(false);
        pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        int dbSizeBeforeAdd = pregledService.findAll().size();

        Pregled dbStudent = null;
        try {
            dbStudent = pregledService.create(pregled);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database
        List<Pregled> pregledi = (List<Pregled>) pregledService.findAll();
        assertEquals(pregledi.size(), dbSizeBeforeAdd+1);

        dbStudent = pregledi.get(pregledi.size() - 1); //get last student
        assertEquals(dbStudent.getId(),6L);
        assertEquals(dbStudent.getDatumVreme(),datum);
        assertEquals(dbStudent.getCena(),1000);
        assertEquals(dbStudent.getTrajanjePregleda(),50);
        assertEquals(dbStudent.getIdTipa(),1L);
        assertEquals(dbStudent.getIdPacijenta(),null);
        assertEquals(dbStudent.getIdKlinike(),1L);
        assertEquals(dbStudent.getIdSale(),1L);
        assertEquals(dbStudent.getIdLekara(),3L);
        assertEquals(dbStudent.isRezervisan(),false);
        assertEquals(dbStudent.isOdradjen(),false);
    }

    @Transactional
    @Rollback
    @Test
    void delete() {

        int dbSizeBeforeRemove = pregledService.findAll().size();
        pregledService.delete(1L);

        List<Pregled> pregledi = (List<Pregled>) pregledService.findAll();
        assertEquals(pregledi.size(),dbSizeBeforeRemove - 1);
    }

    @Transactional
   // @Rollback
    @Test
    void updateKadaZelisDaRezervises() throws Exception {

        Pregled pregled1 = pregledService.findById(4L);
        Pregled pregled2 = pregledService.findById(4L);

        pregled1.setIdPacijenta(3L);
        pregled1.setRezervisan(true);

        pregledService.update(pregled1);

        pregled2.setIdPacijenta(2L);
        pregled1.setRezervisan(true);
        Exception exception = assertThrows(OptimisticLockException.class, () -> {
            pregledService.update(pregled2);});

        assertEquals("Neko pre vas je vec menjao", exception.getMessage());


    }

    @Transactional
    @Rollback
    @Test
    void createAkoJeDatumUProslosti() {
        Pregled pregled = new Pregled();

        String datumUstringu="2020-02-05T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(null);
        pregled.setIdSale(1L);
        pregled.setIdTipa(1L);
       // pregled.setOdradjen(false);
       // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        int dbSizeBeforeAdd = pregledService.findAll().size();

        Pregled dbStudent = null;
        try {
            dbStudent = pregledService.create(pregled);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database

        assertEquals(dbStudent,null);
    }
    @Transactional
    @Rollback
    @Test
    void createAkoJeNekiIdNull() {
        Pregled pregled = new Pregled();

        String datumUstringu="2020-02-09T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(null);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(null);
        pregled.setIdSale(1L);
        pregled.setIdTipa(1L);
        // pregled.setOdradjen(false);
        // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        int dbSizeBeforeAdd = pregledService.findAll().size();

        Pregled dbStudent = null;
        try {
            dbStudent = pregledService.create(pregled);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database

        assertEquals(dbStudent,null);
    }
}