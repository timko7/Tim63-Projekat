package tim63.sistemKlinickogCentar.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.PessimisticLockingFailureException;
import tim63.sistemKlinickogCentar.model.*;
import tim63.sistemKlinickogCentar.repository.*;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;
import tim63.sistemKlinickogCentar.service.TipPregledaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PregledOdZahtevaServiceUnitTest {

    @Autowired
    PregledOdZahtevaService pregledOdZahtevaService;

    @Autowired
    TipPregledaService tipPregledaService;

    @MockBean
    PregledOdZahtevaRepository pregledOdZahtevaRepository;

    @MockBean
    TipPregledaRepositoryInterface tipPregledaRepository;

    @MockBean
    SalaRepositoryInterface salaRepository;

    @MockBean
    KalendarSaleRepository  kalendarSaleRepository;

    @MockBean
    PacijentRepositoryInterface pacijentRepository;

    @MockBean
    LekarRepositoryInterface lekarRepository;

    @BeforeEach
    public void setup() {

        List<PregledOdZahteva> pregledOdZahtevas = new ArrayList<>(0);
        when(
                pregledOdZahtevaRepository.findAll()
        ).thenReturn(
            pregledOdZahtevas
        );


        String datumUstringu="2020-06-27T13:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        PregledOdZahteva pregledOdZahteva = new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1000.0, 1L, 1L, false);
        //pregledOdZahteva.setVerzija(null);
        //pregledOdZahteva.setId(null);
        //pregledOdZahteva.setRezervisan(false);
        //pregledOdZahteva.setOdradjen(false);
        when(
                pregledOdZahtevaRepository.save(any(PregledOdZahteva.class))
        ).thenReturn(
                pregledOdZahteva
        );

        TipPregleda t = new TipPregleda("Tip1", true, 1L, 1000);
        t.setId(1L);
        when(
                tipPregledaRepository.findById(1L)
        ).thenReturn(
                Optional.of(t)
        );

        Sala s = new Sala("S1", true, 1L);
        s.setId(1L);
        when(
                salaRepository.findById(1L)
        ).thenReturn(
                Optional.of(s)
        );

        s.setSlobodna(false);
        when(
                salaRepository.save(s)
        ).thenReturn(
                s
        );

        t.setSlobodan(false);
        when(
                tipPregledaRepository.save(t)
        ).thenReturn(
                t
        );

        String datumUstringuOd = "2020-06-27T13:00:00";
        LocalDateTime datumOd = LocalDateTime.parse(datumUstringu);
        String datumUstringuDo = "2020-06-27T13:45:00";
        LocalDateTime datumDo = LocalDateTime.parse(datumUstringu);
        when(
                kalendarSaleRepository.save(any(KalendarSale.class))
        ).thenReturn(
                new KalendarSale(1L, datumOd, datumDo)
        );


        Pacijent pacijent = new Pacijent("Pera", "Peric", "pero@gmail.com", "petar123", "NS", "SRB", "Bl. Osl. 10", "123123", "12345678");
        pacijent.setId(1L);

        when(
                pacijentRepository.findById(1L)
        ).thenReturn(
                Optional.of(pacijent)
        );


        Lekar lekar = new Lekar("Leka", "Lekaric", "l@gmail.com", "lekaleka", "NS", "SRB", "Bl. Osl. 10", "123123", "12345678", 1L, 1L, true, 5, 20, false);
        lekar.setId(1L);

        when(
                lekarRepository.findById(1L)
        ).thenReturn(
                Optional.of(lekar)
        );

    }


    @Test
    public void testDodajPregledOdZahteva() throws Exception {

        int dbSizeBeforeAdd = pregledOdZahtevaService.findAll().size();

        String datumUstringu = "2020-06-27T13:00";
        LocalDateTime datum = LocalDateTime.parse(datumUstringu);

        PregledOdZahteva pregledOdZahteva = new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false);
        //pregledOdZahteva.setId(1L);
        //pregledOdZahteva.setVerzija(0L);

        PregledOdZahteva dbPregledOdZahteva = pregledOdZahtevaService.create(pregledOdZahteva);
        //try {
        //dbPregledOdZahteva = pregledOdZahtevaService.create(pregledOdZahteva);
        //} catch (Exception e) {
        //   e.printStackTrace();
        //}

        TipPregleda tipTemp = tipPregledaService.findById(1L);

        assertFalse(tipTemp.isSlobodan());

        assertEquals(1L, dbPregledOdZahteva.getIdTipa());
        assertEquals(1L, dbPregledOdZahteva.getIdSale());
        assertEquals(1L, dbPregledOdZahteva.getIdLekara());
        assertEquals(1L, dbPregledOdZahteva.getIdKlinike());
        assertEquals(1L, dbPregledOdZahteva.getIdPacijenta());
        assertFalse(dbPregledOdZahteva.isOdradjen());
        assertEquals(tipTemp.getCena(), dbPregledOdZahteva.getCena());

    }

    @Test
    public void testPessimisticLockingScenario() {
        final CountDownLatch latch = new CountDownLatch(2);
        Runnable r1 = () -> {
            String datumUstringu="2020-06-27T13:00";
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
                assertTrue(e instanceof PessimisticLockingFailureException);
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
