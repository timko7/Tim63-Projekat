package tim63.sistemKlinickogCentar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PregledOdZahtevaKontrolerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;


    @Test
    void dodajPregledOdZahteva() {
        int size = pregledOdZahtevaService.findAll().size(); // broj slogova pre ubacivanja novog

        String datumUstringu="2020-06-27T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ResponseEntity<PregledOdZahteva> responseEntity =
                restTemplate.postForEntity("/api/preglediOdZahteva/add",
                        new PregledOdZahteva(datum, 45, 1L, 1L, 1L, 1600, 1L, 1L, false),
                        PregledOdZahteva.class);

        // provera odgovora servera
        PregledOdZahteva pregledOdZahteva = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregledOdZahteva);
        assertEquals(datum, pregledOdZahteva.getDatumVreme());
        assertEquals(1L, pregledOdZahteva.getIdPacijenta());
        assertEquals(1L, pregledOdZahteva.getIdLekara());
        assertFalse(pregledOdZahteva.isOdradjen());

        Collection<PregledOdZahteva> preglediOdZahtevas = pregledOdZahtevaService.findAll();
        assertEquals(size + 1, preglediOdZahtevas.size()); // mora biti jedan vise slog sada nego pre

        // uklanjamo dodatu torku
        pregledOdZahtevaService.delete(pregledOdZahteva.getId());
    }





}
