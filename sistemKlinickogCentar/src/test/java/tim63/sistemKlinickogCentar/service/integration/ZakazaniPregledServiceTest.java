package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.repository.ZakazaniPregledRepositoryInterface;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;
import tim63.sistemKlinickogCentar.service.SalaService;
import tim63.sistemKlinickogCentar.service.ZakazaniPregledService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZakazaniPregledServiceTest {

    @Autowired
    private ZakazaniPregledRepositoryInterface zpi;

    @Autowired
    private ZakazaniPregledService zakazaniPregledService;

    //private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalaService salaService;

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;

    @Test
    void findAll() {
        List<ZaktaniPregledi> pregledi = (List<ZaktaniPregledi>) zakazaniPregledService.findAll();
        assertEquals(pregledi.size(), 5);
    }

    @Test
    void findByIdLekara() {
        Collection<ZaktaniPregledi> pregledi= zakazaniPregledService.findByIdLekara(2L);

        assertEquals(pregledi.size(), 3);
    }

    @Test
    void findByIdPacijenta() {
        Collection<ZaktaniPregledi> pregledi= zakazaniPregledService.findByIdPacijenta(1L);

        assertEquals(pregledi.size(), 3);
    }

    @Test
    void findByIdKlinike() {
        Collection<ZaktaniPregledi> pregledi= zakazaniPregledService.findByIdKlinike(1L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void findById() {
        ZaktaniPregledi pregled = zakazaniPregledService.findById(2L);

        String datumUstringu="2020-06-28T16:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        assertEquals(pregled.getDatumVreme(),datum);
        assertEquals(pregled.getTrajanjePregleda(),60);
        assertEquals(pregled.getIdSale(),null);
        assertEquals(pregled.getCena(),700);
        assertEquals(pregled.getIdLekara(),1L);
        assertEquals(pregled.getIdKlinike(),2L);
        assertEquals(pregled.getIdPacijenta(),2L);
        assertEquals(pregled.getIdTipa(),2L);
        assertEquals(pregled.isOdradjen(),false);
        assertEquals(pregled.isRezervisan(),false);
    }


    @Transactional
    @Rollback
    @Test
    void odradi() {
        ZaktaniPregledi zaOdradu =zakazaniPregledService.findById(1L);
        zaOdradu.setOdradjen(true);
        zaOdradu = zpi.save(zaOdradu);
        assertEquals(zaOdradu.isOdradjen(),true);
    }

    @Transactional
    @Rollback
    @Test
    void create() {
        ZaktaniPregledi pregled = new ZaktaniPregledi();

        String datumUstringu="2020-03-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(2L);
        pregled.setIdSale(null);
        pregled.setIdTipa(1L);
        pregled.setOdradjen(false);
       // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        int dbSizeBeforeAdd = zakazaniPregledService.findAll().size();

        ZaktaniPregledi dbStudent = null;
        try {
            dbStudent = zakazaniPregledService.create(pregled);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database
        List<ZaktaniPregledi> pregledi = (List<ZaktaniPregledi>) zakazaniPregledService.findAll();
        assertEquals(pregledi.size(), dbSizeBeforeAdd+1);

        dbStudent = pregledi.get(pregledi.size() - 1); //get last student
        assertEquals(dbStudent.getId(),6L);
        assertEquals(dbStudent.getDatumVreme(),datum);
        assertEquals(dbStudent.getCena(),1000);
        assertEquals(dbStudent.getTrajanjePregleda(),50);
        assertEquals(dbStudent.getIdTipa(),1L);
        assertEquals(dbStudent.getIdPacijenta(),2L);
        assertEquals(dbStudent.getIdKlinike(),1L);
        assertEquals(dbStudent.getIdSale(),null);
        assertEquals(dbStudent.getIdLekara(),3L);
        assertEquals(dbStudent.isRezervisan(),true);
        assertEquals(dbStudent.isOdradjen(),false);
    }

    @Transactional
    @Rollback
    @Test
    void delete() {
        int dbSizeBeforeRemove = zakazaniPregledService.findAll().size();
        zakazaniPregledService.delete(1L);

        List<ZaktaniPregledi> pregledi = (List<ZaktaniPregledi>) zakazaniPregledService.findAll();
        assertEquals(pregledi.size(),dbSizeBeforeRemove - 1);
    }

    @Transactional
    @Rollback
    @Test
    void createAkoJeVremeNull() throws Exception {
        ZaktaniPregledi pregled = new ZaktaniPregledi();

        //String datumUstringu="2020-03-28T13:00:00";
        //LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(null);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(2L);
        pregled.setIdSale(null);
        pregled.setIdTipa(1L);
        pregled.setOdradjen(false);
        // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

     assertEquals(zakazaniPregledService.create(pregled),null);

    }

    @Transactional
    @Rollback
    @Test
    void createAkoJeTipNull() throws Exception {
        ZaktaniPregledi pregled = new ZaktaniPregledi();

        String datumUstringu="2020-03-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(2L);
        pregled.setIdSale(null);
        pregled.setIdTipa(null);
        pregled.setOdradjen(false);
        // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        assertEquals(zakazaniPregledService.create(pregled),null);

    }
    @javax.transaction.Transactional
    @Rollback
    @Test
    void createAkoJeDatumUProslosti() throws Exception {
        ZaktaniPregledi pregled = new ZaktaniPregledi();

        String datumUstringu="2020-02-05T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        pregled.setDatumVreme(datum);
        pregled.setTrajanjePregleda(50);
        pregled.setCena(1000);
        pregled.setIdKlinike(1L);
        pregled.setIdLekara(3L);
        pregled.setIdPacijenta(1L);
        pregled.setIdSale(null);
        pregled.setIdTipa(1L);
        // pregled.setOdradjen(false);
        // pregled.setRezervisan(false);
        //pregled.setVerzija(0L);

        assertEquals(zakazaniPregledService.create(pregled),null);
    }

    @Transactional
    @Rollback
    @Test
    void odradiAkoJeNekoVecOdobrio() {
        ZaktaniPregledi zaOdradu1 =zakazaniPregledService.findById(1L);
        ZaktaniPregledi zaOdradu2 =zakazaniPregledService.findById(1L);
        zaOdradu1.setOdradjen(true);
        zaOdradu2.setOdradjen(true);
        zaOdradu1 = zpi.save(zaOdradu1);
        Exception exception = assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            zpi.save(zaOdradu2);});
        String expectedMessage = "Neko je vec menjao";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}