package tim63.sistemKlinickogCentar.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.PretragaKlinike;
import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;
import tim63.sistemKlinickogCentar.repository.LekarRepositoryInterface;
import tim63.sistemKlinickogCentar.repository.ZahtevOdsustvoRepositoryInterface;
import tim63.sistemKlinickogCentar.service.LekarService;
import tim63.sistemKlinickogCentar.service.ZahtevOdsustvoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LekarServiceUnitTest {

    @Autowired
    LekarService lekarService;

    @Autowired
    ZahtevOdsustvoService zahtevOdsustvoService;

    @MockBean
    LekarRepositoryInterface lekarRepositoryInterface;

    @MockBean
    ZahtevOdsustvoRepositoryInterface zahtevOdsustvoRepositoryInterface;

    @BeforeEach
    void setUp() {

        Lekar l1 = new Lekar("Ivana", "Novakovic","i@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",1L,1L,true,1,7,true);
        l1.setId(1L);
        when(
                lekarRepositoryInterface.findById(1L)
        ).thenReturn(
                Optional.of(l1)
        );

        Lekar l2 = new Lekar("Mika", "Novakovic","i@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",1L,1L,true,12,20,true);
        l2.setId(2L);
        when(
                lekarRepositoryInterface.findById(2L)
        ).thenReturn(
                Optional.of(l2)
        );

        when(
                lekarRepositoryInterface.findById(5L)
        ).thenReturn(
                Optional.empty()
        );
//
        when(
                lekarRepositoryInterface.save(new Lekar("Milica", "Lukic","mglukic@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",2L,2L,true,10,18,true))
        ).thenReturn(
                new Lekar("Milica", "Lukic","mglukic@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",2L,2L,true,10,18,true)
        );
//
        List<Lekar> allCountries = new ArrayList<>(5);
        when(
                lekarRepositoryInterface.findAll()
        ).thenReturn(
                allCountries
        );
        List<Lekar> lekariKlinike = new ArrayList<>(2);
        //Lekar s1 = new Lekar("Ivana", "Novakovic","i@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",1L,1L,true,1,7,true);

        lekariKlinike.add(lekarService.findById(1L));
        lekariKlinike.add(lekarService.findById(2L));
       // Lekar s2 = new Lekar("Ivana", "Novakovic","i@gmail.com","33333333","Grad1","Adresa1","Drzava1","111111111","555",1L,1L,true,1,7,true);

        when(
                lekarRepositoryInterface.findByIdKlinike(1L)
        ).thenReturn(
                lekariKlinike
        );
        List<Lekar> lekariTipa = new ArrayList<>(2);
        lekariTipa.add(lekarService.findById(1L));
        lekariTipa.add(lekarService.findById(2L));
        when(
                lekarRepositoryInterface.findByIdTipa(1L)
        ).thenReturn(
                lekariTipa
        );

        List<ZahtevOdsustvo> zahteviPrvogLekara = new ArrayList<>(1);
        zahteviPrvogLekara.add(new ZahtevOdsustvo(1L,LocalDate.parse("2020-06-28"), LocalDate.parse("2020-06-30"), 1L, 1L, true, true));
        when(
                zahtevOdsustvoRepositoryInterface.findByIdLekara(1L)
        ).thenReturn(
                zahteviPrvogLekara
        );
        List<ZahtevOdsustvo> zahteviDrugogLekara = new ArrayList<>(1);
        zahteviDrugogLekara.add(new ZahtevOdsustvo(2L,LocalDate.parse("2020-06-19"), LocalDate.parse("2020-06-25"), 2L, 1L, true, true));
        when(
                zahtevOdsustvoRepositoryInterface.findByIdLekara(2L)
        ).thenReturn(
                zahteviDrugogLekara
        );

    }

    @Test
    void pretrazi() {
        String datumUstringu="2020-06-20";
        LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Adresa1", 1L, 1L, datum);
        ArrayList<Lekar> termini=lekarService.pretrazi(zahtev);
        assertEquals(1,termini.size());
    }

    @Test
    void pretraziLekre() {
        String datumUstringu="2020-06-01";
        LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Mika", "Novakovic",1L, 1L, datum);
        ArrayList<Lekar> termini=lekarService.pretraziLekre(zahtev);
        assertEquals(1,termini.size());
    }

    @Test
    void vratiTermineNaFront() {
        String datumUstringu="2020-06-28";
        LocalDate datum=LocalDate.parse(datumUstringu);
        ArrayList<Integer> termini=lekarService.vratiTermineNaFront(2L,datum);
        assertEquals(6,termini.size());
    }

    @Test
    void vratiTermine() {
        ArrayList<Integer>termini=new ArrayList<Integer>();
        termini=lekarService.vratiTermine(2L);
        assertEquals(8,termini.size());
    }

    @Test
    void vratiTermineAkoJeIdNull() {
        String datumUstringu="2020-06-28";
        LocalDate datum=LocalDate.parse(datumUstringu);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.vratiTermineNaFront(null,datum);});

        assertEquals("Ne moze bez datuma/IdLekara", exception.getMessage());
    }
    @Test
    void vratiTermineAkoJeDatumNull() {
      //  String datumUstringu="2020-06-28";
      //  LocalDate datum=LocalDate.parse(datumUstringu);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.vratiTermineNaFront(2L,null);});

        assertEquals("Ne moze bez datuma/IdLekara", exception.getMessage());
    }
}