package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.model.TipPregleda;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;
import tim63.sistemKlinickogCentar.service.TipPregledaService;

import java.time.LocalDateTime;

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


}
