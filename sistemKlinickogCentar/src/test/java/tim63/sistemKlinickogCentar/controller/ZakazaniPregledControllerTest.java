package tim63.sistemKlinickogCentar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.service.PregledService;
import tim63.sistemKlinickogCentar.service.ZakazaniPregledService;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZakazaniPregledControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ZakazaniPregledService pregledService;

    @Test
    void getPreglede() {
        ResponseEntity<ZaktaniPregledi[]> responseEntity =
                restTemplate.getForEntity("/api/zakazaniPregledi",
                        ZaktaniPregledi[].class);

        ZaktaniPregledi[] countries = responseEntity.getBody();

        assertEquals(5, countries.length);
        assertEquals(500, countries[0].getCena());
        assertEquals(60, countries[0].getTrajanjePregleda());
        assertEquals(700, countries[1].getCena());
        assertEquals(60, countries[1].getTrajanjePregleda());
    }

    @Test
    void getPregledPoID() {
        String datumUstringu="2020-06-28T16:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);

        ResponseEntity<ZaktaniPregledi> responseEntity =
                restTemplate.getForEntity("/api/zakazaniPregledi/3", ZaktaniPregledi.class);

        ZaktaniPregledi pregled = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(pregled);
        assertEquals(datum, pregled.getDatumVreme());
        assertEquals(60, pregled.getTrajanjePregleda());
    }

    @Test
    void getPregledPoIDLekra() {
        ResponseEntity<ZaktaniPregledi[]> responseEntity =
                restTemplate.getForEntity("/api/zakazaniPregledi/uzmiZakazane/1",
                        ZaktaniPregledi[].class);
        ZaktaniPregledi[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);

    }

    @Test
    void getPregledPoIDKlinike() {
        ResponseEntity<ZaktaniPregledi[]> responseEntity =
                restTemplate.getForEntity("/api/zakazaniPregledi/uzmiZakazanePoKlinici/1",
                        ZaktaniPregledi[].class);
        ZaktaniPregledi[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);
    }

    @Test
    void getPregledPoIDPacijenta() {

    ResponseEntity<ZaktaniPregledi[]> responseEntity =
            restTemplate.getForEntity("/api/zakazaniPregledi/uzmiZakazanePacijente/2",
                    ZaktaniPregledi[].class);
    ZaktaniPregledi[] pregled = responseEntity.getBody();

     assertEquals(2,pregled.length);

}

    @Test
    void dodajPregled() {
        int size = pregledService.findAll().size(); // broj slogova pre ubacivanja novog

        String datumUstringu="2020-06-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ResponseEntity<ZaktaniPregledi> responseEntity =
                restTemplate.postForEntity("/api/zakazaniPregledi/add",
                        new ZaktaniPregledi(datum,  1L,  1L, 500, 1L, 2L),
                        ZaktaniPregledi.class);

        // provera odgovora servera
        ZaktaniPregledi pregled = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregled);
        assertEquals(datum, pregled.getDatumVreme());
        assertEquals(500, pregled.getCena());

        Collection<ZaktaniPregledi> countries = pregledService.findAll();
        assertEquals(size + 1, countries.size()); // mora biti jedan vise slog sada nego pre
        // poslednja drzava u listi treba da bude nova drzava koja je ubacena u testu


        // uklanjamo dodatu drzavu
        pregledService.delete(pregled.getId());
    }

    @Test
    void izbiriPregledPoID() throws Exception {
        // ubacimo drzavu koju cemo brisati
        String datumUstringu="2020-06-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ZaktaniPregledi pregled = pregledService.create(new ZaktaniPregledi(datum,  1L,  1L, 500, 1L, 2L));
        // preuzmemo trenutni broj drzava
        int size = pregledService.findAll().size();
        System.out.println(size);
        // poziv REST servisa za brisanje

        restTemplate.delete("/api/zakazaniPregledi/obrisi/" + pregled.getId());


        // provera odgovora servera
        // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, pregledService.findAll().size());
    }

    @Test
    void odradiZahtev() throws Exception {

        String datumUstringu="2020-06-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ZaktaniPregledi p=pregledService.create( new ZaktaniPregledi(datum,  1L,  1L, 500, 1L, 2L));


        ResponseEntity<ZaktaniPregledi> responseEntity =
                restTemplate.exchange("/api/zakazaniPregledi/odradiZahtev/"+p.getId(), HttpMethod.PUT,
                        new HttpEntity<ZaktaniPregledi>(p),
                        ZaktaniPregledi.class);

        // provera odgovora servera
        ZaktaniPregledi pregled = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //assertNotNull(pregled);
        assertEquals(true, pregled.isOdradjen());

        ZaktaniPregledi dbCountry = pregledService.findById(pregled.getId());
        assertEquals(true, dbCountry.isOdradjen());

        // uklanjamo dodatu drzavu
        pregledService.delete(pregled.getId());
    }
}