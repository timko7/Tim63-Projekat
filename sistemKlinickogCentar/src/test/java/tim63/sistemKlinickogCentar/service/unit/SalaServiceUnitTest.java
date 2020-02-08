package tim63.sistemKlinickogCentar.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.repository.KalendarSaleRepository;
import tim63.sistemKlinickogCentar.repository.SalaRepositoryInterface;
import tim63.sistemKlinickogCentar.service.KalendarSaleService;
import tim63.sistemKlinickogCentar.service.SalaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaServiceUnitTest {

    @Autowired
    SalaService salaService;

    @MockBean
    SalaRepositoryInterface salaRepository;

    @MockBean
    KalendarSaleRepository kalendarSaleRepository;

    @BeforeEach
    public void setup() {
        // definisanje ponasanja test dvojnika salaRepository i kalendarSaleRepository
        when(
                salaRepository.findById(1L)
        ).thenReturn(
                Optional.of(new Sala("S1", true, 1L))
        );

        List<Sala> sale = new ArrayList<>(4);
        Sala s1 = new Sala("S1",true,1L);
        s1.setId(1L);
        sale.add(s1);
        Sala s2 = new Sala("Sala2",true,1L);
        s2.setId(2L);
        sale.add(s2);
        Sala s3 = new Sala("Operaciona1",true,1L);
        s3.setId(3L);
        sale.add(s3);
        Sala s4 = new Sala("Sala1",true,1L);
        s4.setId(4L);
        sale.add(s4);

        when(
                salaRepository.findByIdKlinike(1L)
        ).thenReturn(
                sale
        );

        List<KalendarSale> kalendarSales = new ArrayList<>(1);
        kalendarSales.add(new KalendarSale(1L, LocalDateTime.parse("2020-03-03T11:00:00"), LocalDateTime.parse("2020-03-03T12:00:00")));
        when(
                kalendarSaleRepository.findByIdSale(1L)
        ).thenReturn(
                kalendarSales
        );
    }

    @Test
    public void testGetSalaById() {
        Sala sala = salaService.findById(1L);

        assertEquals("S1", sala.getNaziv());
    }

    @Test
    public void testPretraziPoNazivDatumu() {

        Collection<Sala> sale = salaService.pretraziPoNazivuDatumuKlinike(1L, "S1", "2020-03-03T11:30:00");

        assertNotNull(sale);
        //System.out.println("TEST::" + sale.iterator().next().getNaziv());
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
