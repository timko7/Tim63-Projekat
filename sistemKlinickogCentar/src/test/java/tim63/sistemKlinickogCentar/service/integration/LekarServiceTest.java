package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.PretragaKlinike;
import tim63.sistemKlinickogCentar.repository.LekarRepositoryInterface;
import tim63.sistemKlinickogCentar.service.KalendarService;
import tim63.sistemKlinickogCentar.service.LekarService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LekarServiceTest {

    @Autowired
    public LekarRepositoryInterface lekRep;

    @Autowired
    public LekarService lekarService;

    @Autowired
    public KalendarService kalendarService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Lekar> pregledi = (List<Lekar>) lekarService.findAll();
        assertEquals(pregledi.size(), 4);
    }

    @Test
    void findById() {
        Lekar pacijent = lekarService.findById(2L);

        assertEquals(pacijent.getIme(),"Mika");
        assertEquals(pacijent.getPrezime(),"Novakovic");
        assertEquals(pacijent.getEmail(),"a@gmail.com");
        assertEquals(pacijent.getPassword(),"33333333");
        assertEquals(pacijent.getGrad(),"Grad1");
        assertEquals(pacijent.getDrzava(),"Drzava1");
        assertEquals(pacijent.getAdresa(),"Adresa1");
        assertEquals(pacijent.getTelefon(),"111111111");
        assertEquals(pacijent.getBroj_osiguranika(),"555");
        assertEquals(pacijent.getRadnoVremeDo(),20);
        assertEquals(pacijent.getRadnoVremeOd(),12);

    }

    @Test
    void findByIdKlinike() {
        Collection<Lekar> pregledi= lekarService.findByIdKlinike(1L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void findByIdTipa() {
        Collection<Lekar> pregledi= lekarService.findByIdTipa(2L);

        assertEquals(pregledi.size(), 2);
    }

    @Test
    void pretrazi() {
        String datumUstringu="2020-06-29";
        LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Adresa1", 1L, 1L, datum);
        ArrayList<Lekar> termini=lekarService.pretrazi(zahtev);
        ArrayList<Lekar> pom=new ArrayList<>();
        pom.add(lekarService.findById(2L));
        assertEquals(termini.size(), 1);


    }

    @Test
    void pretraziLekre() {
        String datumUstringu="2020-06-29";
        LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Mika", "Novakovic",1L, 1L, datum);
        ArrayList<Lekar> termini=lekarService.pretrazi(zahtev);
        ArrayList<Lekar> pom=new ArrayList<>();
        pom.add(lekarService.findById(2L));
        assertEquals(termini.size(), 1);


    }

    @Test
    void vratiTermineNaFront() {

        String datumUstringu="2020-06-28";
        LocalDate datum=LocalDate.parse(datumUstringu);
        ArrayList<Integer> termini=lekarService.vratiTermineNaFront(2L,datum);
        assertEquals(termini.size(),6);
    }

    @Test
    void vratiTermine() {
        ArrayList<Integer> termini=new ArrayList<Integer>();
        Lekar l=lekarService.findById(1L);
        ArrayList<Integer> pom=new ArrayList<>();
        pom.add(1);
        pom.add(2);
        pom.add(3);
        pom.add(4);
        pom.add(5);
        pom.add(6);
        for(int i=l.getRadnoVremeOd();i<l.getRadnoVremeDo();i++){
            termini.add(i);
        }
        assertEquals(termini,pom);

    }

    @Test
    void findByEmail() {
        Lekar pacijent = lekarService.findByEmail("i@gmail.com");

        assertEquals(pacijent.getIme(),"Ivana");
        assertEquals(pacijent.getPrezime(),"Novakovic");
        assertEquals(pacijent.getPassword(),"33333333");
        assertEquals(pacijent.getGrad(),"Grad1");
        assertEquals(pacijent.getDrzava(),"Drzava1");
        assertEquals(pacijent.getAdresa(),"Adresa1");
        assertEquals(pacijent.getTelefon(),"111111111");
        assertEquals(pacijent.getBroj_osiguranika(),"555");
        assertEquals(pacijent.getRadnoVremeDo(),7);
        assertEquals(pacijent.getRadnoVremeOd(),1);
    }

    @Transactional
    @Rollback
    @Test
    void create() {
        Lekar pacijent = new Lekar();
        pacijent.setIme("Milica");
        pacijent.setPrezime("Lukic");
        pacijent.setEmail("mglukic@gmail.com");
        pacijent.setPassword("88888888");
        pacijent.setGrad("Smederevo");
        pacijent.setDrzava("Srbija");
        pacijent.setAdresa("Adresa");
        pacijent.setTelefon("123456789");
        pacijent.setBroj_osiguranika("123");
        pacijent.setRadnoVremeOd(2);
        pacijent.setRadnoVremeDo(10);
        pacijent.setIdKlinike(1L);
        pacijent.setIdTipa(1L);

        int dbSizeBeforeAdd = lekarService.findAll().size();

        Lekar dbStudent = null;
        try {
            dbStudent = lekRep.save(pacijent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database
        List<Lekar> pacijenti = (List<Lekar>) lekarService.findAll();
        assertEquals(pacijenti.size(), dbSizeBeforeAdd+1);

        dbStudent = pacijenti.get(pacijenti.size() - 1); //get last student
        assertEquals(dbStudent.getId(),5L);
        assertEquals(dbStudent.getIme(),"Milica");
        assertEquals(dbStudent.getPrezime(),"Lukic");
        assertEquals(dbStudent.getPassword(),"88888888");
        assertEquals(dbStudent.getGrad(),"Smederevo");
        assertEquals(dbStudent.getDrzava(),"Srbija");
        assertEquals(dbStudent.getAdresa(),"Adresa");
        assertEquals(dbStudent.getTelefon(),"123456789");
        assertEquals(dbStudent.getBroj_osiguranika(),"123");
        assertEquals(dbStudent.getRadnoVremeOd(),2);
        assertEquals(dbStudent.getRadnoVremeDo(),10);
    }

    @Transactional
    @Rollback
    @Test
    void update() {
        Lekar dbPacijent = lekarService.findById(1L);

        dbPacijent.setIme("Petar");
        dbPacijent.setPrezime("Perovic");
        dbPacijent.setGrad("Smederevo");
        dbPacijent.setRadnoVremeOd(1);
        dbPacijent.setRadnoVremeDo(9);
        try {
            dbPacijent = lekRep.save(dbPacijent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //verify that database contains updated data
        dbPacijent = lekarService.findById(1L);

        assertEquals(dbPacijent.getIme(),"Petar");
        assertEquals(dbPacijent.getPrezime(),"Perovic");
        assertEquals(dbPacijent.getGrad(),"Smederevo");
        assertEquals(dbPacijent.getRadnoVremeOd(),1);
        assertEquals(dbPacijent.getRadnoVremeDo(),9);
    }

    @Transactional
    @Rollback
    @Test
    void promeniLozinku() {
        Lekar lekarZaPromenu = lekarService.findById(1L);
        lekarZaPromenu.setPassword("22222222");
        lekarZaPromenu.setPrviPutLogovan(false);
        lekarZaPromenu = lekRep.save(lekarZaPromenu);
        assertEquals(lekarZaPromenu.getPassword(),"22222222");
        assertEquals(lekarZaPromenu.isPrviPutLogovan(),false);

    }

    @Transactional
    @Rollback
    @Test
    void delete() {
        int dbSizeBeforeRemove = lekarService.findAll().size();
        lekarService.delete(1L);

        List<Lekar> pregledi = (List<Lekar>) lekarService.findAll();
        assertEquals(pregledi.size(),dbSizeBeforeRemove - 1);
    }
    @Test
    void vratiTermineNaFrontAkoNemaDatum() {

        String datumUstringu=null;
       // LocalDate datum=LocalDate.parse(datumUstringu);
        //ArrayList<Integer> termini=lekarService.vratiTermineNaFront(2L,datum);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.vratiTermineNaFront(2L,null);});

        assertEquals("Ne moze bez datuma/IdLekara", exception.getMessage());

        assertTrue(exception.getMessage().contains("datum"));
    }
    @Test
    void vratiTermineNaFrontAkoNemaTip() {

        String datumUstringu="2020-06-28";
         LocalDate datum=LocalDate.parse(datumUstringu);
        //ArrayList<Integer> termini=lekarService.vratiTermineNaFront(2L,datum);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.vratiTermineNaFront(null,datum);});

        assertEquals("Ne moze bez datuma/IdLekara", exception.getMessage());

        assertTrue(exception.getMessage().contains("datum"));
    }

    @Test
    void vratiTermineNaFrontAkoJeDatumProsao() {

        String datumUstringu="2020-02-05";
        LocalDate datum=LocalDate.parse(datumUstringu);
        ArrayList<Integer> termini=lekarService.vratiTermineNaFront(2L,datum);
        assertEquals(termini, null);

    }
    @Test
    void pretraziAkoNemaTip() {
        String datumUstringu="2020-06-29";
        LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Adresa1",null,1l,datum);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.pretrazi(zahtev);});

        assertEquals("Ne moze bez datuma/IdTipa", exception.getMessage());

        assertTrue(exception.getMessage().contains("datum"));

    }
    @Test
    void pretraziAkoNemaDatum() {
        //String datumUstringu="2020-06-29";
        //LocalDate datum=LocalDate.parse(datumUstringu);
        PretragaKlinike zahtev=new PretragaKlinike("Adresa1",1L,1l,null);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            lekarService.pretrazi(zahtev);});

        assertEquals("Ne moze bez datuma/IdTipa", exception.getMessage());

        assertTrue(exception.getMessage().contains("datum"));

    }
}