package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;
import tim63.sistemKlinickogCentar.service.SalaService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaServiceIntegrationTest {

    @Autowired
    public SalaService salaService;

    @Test
    void testFindByIdKlinike() {
        Collection<Sala> sale = salaService.findByIdKlinike(1L);

        assertEquals(4, sale.size());
    }

    @Test
    void testFindById() {
        Sala sala = salaService.findById(1L);

        assertEquals("S1", sala.getNaziv());
    }

    @Test
    public void testPretraziPoNazivDatumu() {

        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, "S1", "2020-03-03T11:30:00");

        assertNotNull(sale);
        assertEquals(0, sale.size());
    }

    @Test
    public void testPretraziPoNazivDatumu2() {
        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, "S1", "2020-03-02T11:30:00");

        assertNotNull(sale);
        assertEquals(1, sale.size());
        assertEquals("S1", sale.iterator().next().getNaziv());

    }


    @Test
    public void testPretraziPoNazivDatumuPrazanNaziv() {
        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, " ", "2020-03-02T11:30:00");

        assertNotNull(sale);
        assertEquals(0, sale.size());
    }

    @Test
    public void testPretraziPoNazivDatumuPrazanDatum() {
        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, "S1", " ");

        assertNotNull(sale);
        assertEquals(0, sale.size());
    }


    @Test
    public void testPretraziPoNazivDatumuPrazanDatumPrazanNaziv() {
        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, " ", " ");

        assertNotNull(sale);
        assertEquals(0, sale.size());
    }


    @Test
    public void testPretraziPoNazivDatumuLosFormatDatuma() {
        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, "S1", "asdasd");

        assertNotNull(sale);
        assertEquals(0, sale.size());
    }


    @Test
    public void testPretraziPrviSledeciSlobodanTermin() {
        SalaDatumDTO salaDatumDTO = salaService.getPrviSledeciSlobodanTermin(1L, "2020-03-03T11:30:00");

        assertNotNull(salaDatumDTO);

        System.out.println("DTO: Datum:" + salaDatumDTO.getDatum() + "\nSala:: " + salaDatumDTO.getSala().getNaziv());

        assertEquals("Sala2", salaDatumDTO.getSala().getNaziv());
        assertEquals(1L, salaDatumDTO.getSala().getIdKlinike());

    }

    @Test
    public void testPretraziPrviSledeciSlobodanTerminLosDatum() {
        SalaDatumDTO salaDatumDTO = salaService.getPrviSledeciSlobodanTermin(1L, "asdasd");

        assertNotNull(salaDatumDTO);

        assertNull(salaDatumDTO.getSala());
        assertNull(salaDatumDTO.getDatum());
    }






}
