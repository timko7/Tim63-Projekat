package tim63.sistemKlinickogCentar.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.PessimisticLockingFailureException;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.repository.ZakazaniPregledRepositoryInterface;
import tim63.sistemKlinickogCentar.service.ZakazaniPregledService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZakazaniPregledServiceUnitTest {

    @Autowired
    ZakazaniPregledService pregledService;

    @MockBean
    ZakazaniPregledRepositoryInterface pregledRepositoryInterface;



    @BeforeEach
    void setUp() {
        when(
                pregledRepositoryInterface.findById(2L)
        ).thenReturn(
                Optional.of(new ZaktaniPregledi(LocalDateTime.parse("2020-06-28T16:00:00"),  2L,  1L, 700, 2L, 2L))
        );


        when(
                pregledRepositoryInterface.findById(6L)
        ).thenReturn(
                Optional.empty()
        );
        ZaktaniPregledi p=new ZaktaniPregledi(LocalDateTime.parse("2020-03-28T13:00:00"),1L,  1L, 1500, 1L, 2L);
        when(
                pregledRepositoryInterface.save(any(ZaktaniPregledi.class))
        ).thenReturn(p);
//
        List<ZaktaniPregledi> allCountries = new ArrayList<>(5);
        when(
                pregledRepositoryInterface.findAll()
        ).thenReturn(
                allCountries
        );
    }

    @Test
    void create() throws Exception {
        String datumUstringu="2020-06-28T16:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ZaktaniPregledi zNovi=pregledService.create(new ZaktaniPregledi(datum,  1L,  1L, 1500, 1L, 2L));
        assertEquals(1500,zNovi.getCena());
    }

    @Test
    public void testPessimisticLockingScenario() {
        final CountDownLatch latch = new CountDownLatch(2);
        Runnable r1 = () -> {
            String datumUstringu="2020-06-29T16:00:00";
            LocalDateTime datum=LocalDateTime.parse(datumUstringu);
            ZaktaniPregledi zNovi=pregledService.create(new ZaktaniPregledi(datum,  1L,  1L, 1500, 1L, 2L));
            latch.countDown();
        };

        Runnable r2 = () -> {
            try { Thread.sleep(30); } catch (InterruptedException e) { }

            try {
                String datumUstringu="2020-06-29T16:00:00";
                LocalDateTime datum=LocalDateTime.parse(datumUstringu);
                ZaktaniPregledi zNovi=pregledService.create(new ZaktaniPregledi(datum,  1L,  1L, 1500, 1L, 2L));
                //fail();
            }catch(Exception e) {
                String datumUstringu="2020-06-29T16:00:00";
                LocalDateTime datum=LocalDateTime.parse(datumUstringu);
                assertTrue(e instanceof PessimisticLockingFailureException);
                assertEquals("Zahtev je vec kreiran u trazenom terminu,osvezite stranicu",pregledService.create(new ZaktaniPregledi(datum,  1L,  1L, 1500, 1L, 2L)));
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