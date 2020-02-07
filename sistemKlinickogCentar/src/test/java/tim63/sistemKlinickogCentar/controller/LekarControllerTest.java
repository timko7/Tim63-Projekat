package tim63.sistemKlinickogCentar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.Ocena;
import tim63.sistemKlinickogCentar.model.OcenaKlinike;
import tim63.sistemKlinickogCentar.model.PretragaKlinike;
import tim63.sistemKlinickogCentar.service.LekarService;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LekarControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LekarService lekarService;

    @Test
    void getLekari() {
        ResponseEntity<Lekar[]> responseEntity =
                restTemplate.getForEntity("/api/lekari",
                        Lekar[].class);

        Lekar[] countries = responseEntity.getBody();

        assertEquals(4, countries.length);
        assertEquals("Ivana", countries[0].getIme());
        assertEquals("Novakovic", countries[0].getPrezime());
        assertEquals("Mika", countries[1].getIme());
        assertEquals("Novakovic", countries[1].getPrezime());
    }

    @Test
    void getLekarePoKlinici() {
        ResponseEntity<Lekar[]> responseEntity =
                restTemplate.getForEntity("/api/lekari/1",
                        Lekar[].class);
        Lekar[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);
    }

    @Test
    void getLekarePoTipu() {
        ResponseEntity<Lekar[]> responseEntity =
                restTemplate.getForEntity("/api/lekari/vratiPoTipu/1",
                        Lekar[].class);
        Lekar[] pregled = responseEntity.getBody();

        assertEquals(2, pregled.length);
    }

    @Test
    void dodajLekara() {
        int size = lekarService.findAll().size(); // broj slogova pre ubacivanja novog

        ResponseEntity<Lekar> responseEntity =
                restTemplate.postForEntity("/api/lekari/dodajLekara",
                        new Lekar("Pera","Peric", "p@gmail.com","77777777", "Grad1",
                                "Drzava1", "Adresa1", "123", "123", 1L,1L, true, 2, 8, true),
                        Lekar.class);

        // provera odgovora servera
        Lekar pregled = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //assertNotNull(pregled);
        assertEquals("Pera", pregled.getIme());
        assertEquals("Peric", pregled.getPrezime());
        assertEquals("p@gmail.com", pregled.getEmail());

        Collection<Lekar> countries = lekarService.findAll();
        assertEquals(size + 1, countries.size()); // mora biti jedan vise slog sada nego pre
        // poslednja drzava u listi treba da bude nova drzava koja je ubacena u testu


        // uklanjamo dodatu drzavu
        lekarService.delete(pregled.getId());
    }

    @Test
    void loadById() {


        ResponseEntity<Lekar> responseEntity =
                restTemplate.getForEntity("/api/lekari/lekar/3", Lekar.class);

        Lekar pregled = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(pregled);
        assertEquals("Ivana", pregled.getIme());
        assertEquals("Novakovic", pregled.getPrezime());
        assertEquals("b@gmail.com", pregled.getEmail());
    }

    @Test
    void izmeniLekara() throws Exception {

        Lekar l=lekarService.create(new Lekar("Pera","Peric", "p@gmail.com","77777777", "Grad1",
                "Drzava1", "Adresa1", "123", "123", 1L,1L, true, 2, 8, true));
       l.setIme("Mika");
        l.setPrezime("Mikic");
        l.setRadnoVremeDo(9);
        ResponseEntity<Lekar> responseEntity =
                restTemplate.exchange("/api/lekari/"+l.getId(),
                        HttpMethod.PUT,new HttpEntity<Lekar>(l), Lekar.class);

        Lekar pregledNovi = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregledNovi);
        assertEquals(Long.valueOf(pregledNovi.getId()), pregledNovi.getId());
        assertEquals("Mika", pregledNovi.getIme());
        assertEquals("Mikic", pregledNovi.getPrezime());
        assertEquals(9, pregledNovi.getRadnoVremeDo());


        // provera da li je izmenjen slog u bazi
        Lekar dbCountry = lekarService.findById(pregledNovi.getId());
        assertEquals(Long.valueOf(pregledNovi.getId()), dbCountry.getId());
        assertEquals("Mika", pregledNovi.getIme());
        assertEquals("Mikic", pregledNovi.getPrezime());
        assertEquals(9, pregledNovi.getRadnoVremeDo());

        lekarService.delete(dbCountry.getId());
    }

    @Test
    void oceniLekra() {
        ResponseEntity<Ocena> responseEntity =
                restTemplate.postForEntity("/api/lekari/oceniLekara/1",
                        new Double(5),
                        Ocena.class);
        Ocena pregled = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregled);
        assertEquals(5, pregled.getOcena());
    }


    @Test
    void srednjaOcena() {
        ResponseEntity<Double> responseEntity =
                restTemplate.getForEntity("/api/lekari/srednjaOcena/1",
                        Double.class);

        Double countries = responseEntity.getBody();

        assertEquals(4, countries);
    }

    @Test
    void izmeniPassLekara() throws Exception {

        Lekar l=lekarService.create(new Lekar("Pera","Peric", "p@gmail.com","77777777", "Grad1",
                "Drzava1", "Adresa1", "123", "123", 1L,1L, true, 2, 8, false));

       // l.setPrviPutLogovan(false);
       // l.setPassword("88888888");

        ResponseEntity<Lekar> responseEntity =
                restTemplate.exchange("/api/lekari/promeniLozinku/"+l.getId(),
                        HttpMethod.PUT,new HttpEntity<String>("88888888"), Lekar.class);

        Lekar pregledNovi = responseEntity.getBody();
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(pregledNovi);
        assertEquals(Long.valueOf(l.getId()), pregledNovi.getId());
        assertEquals(false, pregledNovi.isPrviPutLogovan());
        assertEquals("88888888", pregledNovi.getPassword());


        // provera da li je izmenjen slog u bazi
        Lekar dbCountry = lekarService.findById(pregledNovi.getId());
        assertEquals(false, dbCountry.isPrviPutLogovan());
        assertEquals("88888888", dbCountry.getPassword());

        lekarService.delete(dbCountry.getId());
    }

    @Test
    void izbrisiLekara() throws Exception {
        Lekar l=lekarService.create(new Lekar("Pera","Peric", "p@gmail.com","77777777", "Grad1",
                "Drzava1", "Adresa1", "123", "123", 1L,1L, true, 2, 8, true));
        // preuzmemo trenutni broj drzava
        int size = lekarService.findAll().size();
        System.out.println(size);
        // poziv REST servisa za brisanje

        restTemplate.delete("/api/lekari/" + l.getId());

        // provera odgovora servera
        // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, lekarService.findAll().size());
    }

    @Test
    void pretrazi() {

        String datumUstringu="2020-06-29";
        LocalDate datum=LocalDate.parse(datumUstringu);
        ResponseEntity<Lekar[]> responseEntity =
                restTemplate.postForEntity("/api/lekari/pretraziLekarePaVratiKlinike",
                        new PretragaKlinike("Adresa1",1L, 1L,datum),
                        Lekar[].class);

        // provera odgovora servera
        Lekar[] pregled = responseEntity.getBody();
        //assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //assertNotNull(pregled);
        assertEquals(pregled.length, 1);

    }

    @Test
    void pretraziLekare() {
        String datumUstringu="2020-06-29";
        LocalDate datum=LocalDate.parse(datumUstringu);
        ResponseEntity<Lekar[]> responseEntity =
                restTemplate.postForEntity("/api/lekari/pretraziLekare",
                        new PretragaKlinike("Mika", "Novakovic",1L, 1L, datum),
                        Lekar[].class);

        // provera odgovora servera
        Lekar[] pregled = responseEntity.getBody();
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //assertNotNull(pregled);
        assertEquals(pregled.length, 1);
    }

    @Test
    void vratiTermineNaFront() {
        ResponseEntity<Integer[]> responseEntity =
                restTemplate.getForEntity("/api/lekari/vratiTermine/2?datum=2020-06-28",
                        Integer[].class);

        Integer[] countries = responseEntity.getBody();
        assertEquals(countries.length,6);
    }
}