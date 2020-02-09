package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.model.TipPregleda;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;
import tim63.sistemKlinickogCentar.service.TipPregledaService;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PregledOdZahtevaServiceIntegrationTest {

    @Autowired
    public PregledOdZahtevaService pregledOdZahtevaService;

    @Autowired
    public TipPregledaService tipPregledaService;

    @Test
    void testDodajPregledOdZahteva() throws Exception {

        int dbSizeBeforeAdd = pregledOdZahtevaService.findAll().size();

        String datumUstringu = "2020-06-27T13:00:00";
        LocalDateTime datum = LocalDateTime.parse(datumUstringu);

        PregledOdZahteva pregledOdZahteva = new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false);

        PregledOdZahteva dbPregledOdZahteva = pregledOdZahtevaService.create(pregledOdZahteva);
        //try {
            //dbPregledOdZahteva = pregledOdZahtevaService.create(pregledOdZahteva);
        //} catch (Exception e) {
         //   e.printStackTrace();
        //}

        TipPregleda tipTemp = tipPregledaService.findById(dbPregledOdZahteva.getIdTipa());

        assertEquals(dbSizeBeforeAdd + 1, pregledOdZahtevaService.findAll().size());

        assertFalse(tipTemp.isSlobodan());

        assertEquals(1L, dbPregledOdZahteva.getIdTipa());
        assertEquals(1L, dbPregledOdZahteva.getIdSale());
        assertEquals(1L, dbPregledOdZahteva.getIdLekara());
        assertEquals(1L, dbPregledOdZahteva.getIdKlinike());
        assertEquals(1L, dbPregledOdZahteva.getIdPacijenta());
        assertFalse(dbPregledOdZahteva.isOdradjen());
        assertEquals(tipTemp.getCena(), dbPregledOdZahteva.getCena());

        pregledOdZahtevaService.delete(dbPregledOdZahteva.getId());

    }

    @Transactional
    @Rollback
    @Test
    public void testPessimisticLockingScenario() {
        final CountDownLatch latch = new CountDownLatch(2);
        Runnable r1 = () -> {
            String datumUstringu="2020-06-29T16:00:00";
            LocalDateTime datum=LocalDateTime.parse(datumUstringu);
            try {
                PregledOdZahteva zNovi=pregledOdZahtevaService.create(new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false));
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        };

        Runnable r2 = () -> {
            try { Thread.sleep(30); } catch (InterruptedException e) { }

            try {
                String datumUstringu="2020-06-29T16:00:00";
                LocalDateTime datum=LocalDateTime.parse(datumUstringu);
                PregledOdZahteva zNovi=pregledOdZahtevaService.create(new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false));
                //fail();
            }catch(Exception e) {
                String datumUstringu="2020-06-29T16:00:00";
                LocalDateTime datum=LocalDateTime.parse(datumUstringu);
                assertTrue(e instanceof RuntimeException);
                //assertEquals("Zahtev je vec kreiran u trazenom terminu,osvezite stranicu",pregledOdZahtevaService.create(new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false)));
            }

            latch.countDown();
        };

        new Thread(r1).start();
        new Thread(r2).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
