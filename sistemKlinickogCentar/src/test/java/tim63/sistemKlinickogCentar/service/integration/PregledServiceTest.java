package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.service.PregledService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PregledServiceTest {



    @Autowired
    private PregledService pregledService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Pregled> pregledi = (List<Pregled>) pregledService.findAll();
        assertEquals(pregledi.size(), 10);
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
        Collection<Pregled> pregledi= pregledService.findByIdLekara(1L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void findByIdPacijenta() {
        Collection<Pregled> pregledi= pregledService.findByIdKlinike(22L);

        assertEquals(pregledi.size(), 1);
    }

    @Test
    void findById() {

        Pregled pregled = pregledService.findById(2L);

        assertEquals(pregled.getDatumVreme(),"2020-03-28T13:00:00");
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
    @Rollback(true)
    void update() throws Exception {
        Pregled pregled1 = pregledService.findById(1L);
       // Pregled pregled2 = pregledService.findById(1L);

        //modifikovanje istog objekta
        pregled1.setIdPacijenta(2l);
        pregled1.setRezervisan(true);



        //verzija oba objekta je 0
        assertEquals(0, pregled1.getVerzija().intValue());
       // assertEquals(0, pregled2.getVerzija().intValue());

        //pokusaj cuvanja prvog objekta
        pregledService.update(pregled1);

    }

    @Test
    @Rollback(true)
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
        assertEquals(dbStudent.getDatumVreme(),"2020-03-28T13:00:00");
        assertEquals(dbStudent.getCena(),1000);
        assertEquals(dbStudent.getTrajanjePregleda(),50);
        assertEquals(dbStudent.getIdTipa(),1L);
        assertEquals(dbStudent.getIdPacijenta(),2L);
        assertEquals(dbStudent.getIdKlinike(),1L);
        assertEquals(dbStudent.getIdSale(),1L);
        assertEquals(dbStudent.getIdLekara(),3L);
        assertEquals(dbStudent.isRezervisan(),false);
        assertEquals(dbStudent.isOdradjen(),false);
    }

    @Test
    @Rollback(true)
    void delete() {

        int dbSizeBeforeRemove = pregledService.findAll().size();
        pregledService.delete(24L);

        List<Pregled> pregledi = (List<Pregled>) pregledService.findAll();
        assertEquals(pregledi.size(),dbSizeBeforeRemove - 1);
    }

    @Test
    @Rollback(true)
    void updateKadaZelisDaRezervises() throws Exception {
        Pregled pregled1 = pregledService.findById(23L);
        Pregled pregled2 = pregledService.findById(23L);

        //modifikovanje istog objekta
        pregled1.setIdPacijenta(23L);
        pregled1.setRezervisan(true);

        pregled2.setIdPacijenta(22L);
        pregled1.setRezervisan(true);

        //verzija oba objekta je 0
        assertEquals(0, pregled1.getVerzija().intValue());
        assertEquals(2, pregled2.getVerzija().intValue());

        //pokusaj cuvanja prvog objekta
        pregledService.update(pregled1);

        //pokusaj cuvanja prvog objekta
        Exception exception = assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            pregledService.update(pregled2);});
        String expectedMessage = "Neko je vec menjao";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}