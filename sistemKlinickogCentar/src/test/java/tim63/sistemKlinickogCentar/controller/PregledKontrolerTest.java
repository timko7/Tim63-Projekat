package tim63.sistemKlinickogCentar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.service.PregledService;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PregledKontrolerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PregledService pregledService;

    @Test
    void getPreglede() {
        ResponseEntity<Pregled[]> responseEntity =
                restTemplate.getForEntity("/api/pregledi",
                        Pregled[].class);

        Pregled[] countries = responseEntity.getBody();

        assertEquals(5, countries.length);

    }

    @Test
    void getPregledPoID() {

        String datumUstringu="2020-04-28T14:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);

        ResponseEntity<Pregled> responseEntity =
                restTemplate.getForEntity("/api/pregledi/3", Pregled.class);

        Pregled pregled = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(pregled);
        assertEquals(datum, pregled.getDatumVreme());
        assertEquals(120, pregled.getTrajanjePregleda());
    }

    @Test
    void getPregledPoIDKlinike() {
        ResponseEntity<Pregled[]> responseEntity =
                restTemplate.getForEntity("/api/pregledi/uzmiPreglede/1",
                        Pregled[].class);
        Pregled[] pregled = responseEntity.getBody();

        assertEquals(3, pregled.length);


    }

    @Test
    void nadjiPoTipu() {
        ResponseEntity<Pregled[]> responseEntity =
                restTemplate.getForEntity("/api/pregledi/vratiPoTipu/1",
                        Pregled[].class);
        Pregled[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);
    }

    @Test
    void getPregledPoIDLekra() {
        ResponseEntity<Pregled[]> responseEntity =
                restTemplate.getForEntity("/api/pregledi/vratiPoLekaru/2",
                        Pregled[].class);
        Pregled[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);
    }

    @Test
    void getPregledPoIDPacijenta() {
        ResponseEntity<Pregled[]> responseEntity =
                restTemplate.getForEntity("/api/pregledi/vratiPoPacijentu/2",
                        Pregled[].class);
        Pregled[] pregled = responseEntity.getBody();

        assertEquals(1, pregled.length);
    }

    @Test
    void dodajPregled() {
        int size = pregledService.findAll().size(); // broj slogova pre ubacivanja novog

        String datumUstringu="2020-06-27T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        ResponseEntity<Pregled> responseEntity =
                restTemplate.postForEntity("/api/pregledi/add",
                        new Pregled(datum, 50, 1L, 1L, 1L,600,1L),
                        Pregled.class);

        // provera odgovora servera
        Pregled pregled = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //assertNotNull(pregled);
        assertEquals(datum, pregled.getDatumVreme());
        assertEquals(50, pregled.getTrajanjePregleda());
        assertEquals(600, pregled.getCena());

        Collection<Pregled> countries = pregledService.findAll();
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
        Pregled pregled = pregledService.create(new Pregled(datum, 50, 1L, 1L, 1L,600,1L));
        // preuzmemo trenutni broj drzava
        int size = pregledService.findAll().size();
    System.out.println(size);
        // poziv REST servisa za brisanje

                restTemplate.delete("/api/pregledi/obrisi/" + pregled.getId());

        // provera odgovora servera
       // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, pregledService.findAll().size());
    }

    @Test
    void izmeniRezervisan() throws Exception {


        String datumUstringu="2020-06-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        Pregled pregled = pregledService.create(new Pregled(datum, 50, 1L, 1L, 1L,600,1L));

        pregled.setRezervisan(true);
        pregled.setIdPacijenta(1L);
        ResponseEntity<Pregled> responseEntity =
                restTemplate.exchange("/api/pregledi/zakaziPregled/"+pregled.getId(),
                        HttpMethod.PUT,new HttpEntity<Pregled>(pregled), Pregled.class);

        Pregled pregledNovi = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregledNovi);
        assertEquals(Long.valueOf(pregled.getId()), pregledNovi.getId());
        assertEquals(true, pregledNovi.isRezervisan());
        assertEquals(1, pregledNovi.getIdSale().intValue());
        assertEquals(1, pregledNovi.getIdPacijenta().intValue());


        // provera da li je izmenjen slog u bazi
        Pregled dbCountry = pregledService.findById(pregledNovi.getId());
        assertEquals(Long.valueOf(pregledNovi.getId()), dbCountry.getId());
        assertEquals(true, dbCountry.isRezervisan());
        assertEquals(1, dbCountry.getIdSale().intValue());
        assertEquals(1, dbCountry.getIdPacijenta().intValue());

        pregledService.delete(dbCountry.getId());


    }

    @Test
    void izmeniOdradjen() throws Exception {
        String datumUstringu="2020-06-28T13:00:00";
        LocalDateTime datum=LocalDateTime.parse(datumUstringu);
        Pregled pregled = pregledService.create(new Pregled(datum, 50, 1L, 1L, 1L,600,1L));

        pregled.setRezervisan(true);
        pregled.setOdradjen(true);
        pregled.setIdPacijenta(1L);
        ResponseEntity<Pregled> responseEntity =
                restTemplate.exchange("/api/pregledi/odradiPregled/"+pregled.getId(),
                        HttpMethod.PUT,new HttpEntity<Pregled>(pregled), Pregled.class);

        Pregled pregledNovi = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregledNovi);
        assertEquals(Long.valueOf(pregled.getId()), pregledNovi.getId());
        assertEquals(true, pregledNovi.isRezervisan());
        assertEquals(true, pregledNovi.isOdradjen());
        assertEquals(1, pregledNovi.getIdPacijenta().intValue());


        // provera da li je izmenjen slog u bazi
        Pregled dbCountry = pregledService.findById(pregledNovi.getId());
        assertEquals(Long.valueOf(pregledNovi.getId()), dbCountry.getId());
        assertEquals(true, dbCountry.isRezervisan());
        assertEquals(true, dbCountry.isOdradjen());
        assertEquals(1, dbCountry.getIdPacijenta().intValue());

        pregledService.delete(dbCountry.getId());
    }
}