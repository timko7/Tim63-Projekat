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
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.service.SalaService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaKontrolerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SalaService salaService;


    @Test
    public void testGetSale() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale", Sala[].class);

        Sala[] sale = responseEntity.getBody();

        assertEquals(5, sale.length);
        assertEquals("S1", sale[0].getNaziv());
        assertEquals("Sala2", sale[1].getNaziv());
        assertEquals("Operaciona1", sale[2].getNaziv());
        assertEquals("Sala1", sale[4].getNaziv());
        assertEquals(1, sale[3].getIdKlinike());

    }

    @Test
    public void testGetSalePoKlinici() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/2", Sala[].class);

        Sala[] sale = responseEntity.getBody();

        assertEquals(1, sale.length);
        assertEquals("Sala1", sale[0].getNaziv());
        assertEquals(2, sale[0].getIdKlinike());

    }


    @Test
    public void testGetSalePoKlinici2() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/2222", Sala[].class);

        Sala[] sale = responseEntity.getBody();

        assertEquals(0, sale.length);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    public void testGetSaluPoId() {
        ResponseEntity<Sala> responseEntity = restTemplate.getForEntity("/api/sale/uzmiSalu/3", Sala.class);

        Sala sala = responseEntity.getBody();

        assertEquals("Operaciona1", sala.getNaziv());
        assertTrue(sala.isSlobodna());

    }

    @Test
    public void testDodajSalu() {
        int size = salaService.findAll().size(); // broj slogova pre ubacivanja novog

        ResponseEntity<Sala> responseEntity = restTemplate.postForEntity("/api/sale/add", new Sala("Sala2", true, 2L), Sala.class);

        // provera odgovora servera
        Sala sala = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Sala2", sala.getNaziv());
        assertEquals(2L, sala.getIdKlinike());
        assertEquals(true, sala.isSlobodna());

        Collection<Sala> sale = salaService.findAll();
        assertEquals(size + 1, sale.size()); // mora biti jedan vise slog sada nego pre
        // poslednja sala u listi treba da bude nova sala koja je ubacena u testu


        // uklanjamo dodatu salu
        salaService.delete(sala.getId());

    }

    @Test
    public void testIzmeniSalu() throws Exception {
        Sala s = salaService.create(new Sala("Sala2", true, 2L));
        s.setNaziv("Sala3");
        s.setSlobodna(false);

        ResponseEntity<Sala> responseEntity = restTemplate.exchange("/api/sale/izmeni/" + s.getNaziv(), HttpMethod.PUT, new HttpEntity<Sala>(s), Sala.class);

        Sala salaNova = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(salaNova);
        assertEquals("Sala3", salaNova.getNaziv());
        assertFalse(salaNova.isSlobodna());

        // provera da li je izmenjen slog u bazi
        Sala dbSala = salaService.findById(salaNova.getId());
        assertEquals(salaNova.getId(), dbSala.getId());
        assertEquals("Sala3", dbSala.getNaziv());
        assertFalse(dbSala.isSlobodna());

        salaService.delete(dbSala.getId());

    }

    @Test
    public void testPretraziPoNazivDatumu() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/S1/2020-03-03T11:30:00", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertEquals(0, sale.length);
    }

    @Test
    public void testPretraziPoNazivDatumuPrazanNaziv() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/ /2020-03-03T11:30:00", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertNotNull(sale);
        assertEquals(0, sale.length);
    }

    @Test
    public void testPretraziPoNazivDatumuPrazanDatum() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/S1/ ", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertNotNull(sale);
        assertEquals(0, sale.length);
    }

    @Test
    public void testPretraziPoNazivDatumuPrazanDatumPrazanNaziv() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/ / ", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertNotNull(sale);
        assertEquals(0, sale.length);
    }

    @Test
    public void testPretraziPoNazivDatumuLosFormatDatuma() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/S1/asdasdasd", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertNotNull(sale);
        assertEquals(0, sale.length);
    }


    @Test
    public void testPretraziPoNazivDatumu2() {
        ResponseEntity<Sala[]> responseEntity = restTemplate.getForEntity("/api/sale/pretrazi/1/S1/2020-03-02T11:30:00", Sala[].class);

        Sala[] sale = responseEntity.getBody();
        assertNotNull(sale);
        assertEquals(1, sale.length);
        assertEquals("S1", sale[0].getNaziv());

    }

    @Test
    public void testPretraziPrviSledeciSlobodanTermin() {
        ResponseEntity<SalaDatumDTO> responseEntity = restTemplate.getForEntity("/api/sale/prvaSlobodna/1/2020-03-03T11:30:00", SalaDatumDTO.class);

        SalaDatumDTO salaDatumDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(salaDatumDTO);

        System.out.println("DTO: Datum:" + salaDatumDTO.getDatum() + "\nSala:: " + salaDatumDTO.getSala().getNaziv());

        assertEquals(1L, salaDatumDTO.getSala().getIdKlinike());

    }


    @Test
    public void testPretraziPrviSledeciSlobodanTerminLosDatum() {
        ResponseEntity<SalaDatumDTO> responseEntity = restTemplate.getForEntity("/api/sale/prvaSlobodna/1/asdasd", SalaDatumDTO.class);

        SalaDatumDTO salaDatumDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(salaDatumDTO);

        assertNull(salaDatumDTO.getSala());
    }


    @Test
    void testIzbrisiSalu() throws Exception {
        Sala s = salaService.create(new Sala("ASD", true, 1L));
        // preuzmemo trenutni broj drzava
        int size = salaService.findAll().size();

        // poziv REST servisa za brisanje

        restTemplate.delete("/api/sale/obrisi/" + s.getId());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, salaService.findAll().size());
    }

}


