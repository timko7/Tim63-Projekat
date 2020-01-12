package tim63.sistemKlinickogCentar.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySources;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.service.PacijentService;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PacijentServiceTest {

    @Autowired
    private PacijentService pacijentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Pacijent> pacijenti = (List<Pacijent>) pacijentService.findAll();
        assertEquals(pacijenti.size(), 3);
    }

    @Test
    void findById() {
        Pacijent pacijent = pacijentService.findById(2L);

        assertEquals(pacijent.getIme(),"Milan");
        assertEquals(pacijent.getPrezime(),"Milanović");
        assertEquals(pacijent.getPassword(),"22222222");
        assertEquals(pacijent.getGrad(),"Grad1");
        assertEquals(pacijent.getDrzava(),"Drzava1");
        assertEquals(pacijent.getAdresa(),"Adresa1");
        assertEquals(pacijent.getTelefon(),"111111111");
        assertEquals(pacijent.getBroj_osiguranika(),"344");
    }

    @Test
    void findByEmail() {
        Pacijent pacijent = pacijentService.findByEmail("m@gmail.com");


        assertEquals(pacijent.getId(),1L);
        assertEquals(pacijent.getIme(),"Marko");
        assertEquals(pacijent.getPrezime(),"Marković");
        assertEquals(pacijent.getPassword(),"11111111");
        assertEquals(pacijent.getGrad(),"Grad1");
        assertEquals(pacijent.getDrzava(),"Drzava1");
        assertEquals(pacijent.getAdresa(),"Adresa1");
        assertEquals(pacijent.getTelefon(),"111111111");
        assertEquals(pacijent.getBroj_osiguranika(),"3324");

    }

    @Test
    @Transactional
    @Rollback(true)
    void create() {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Milica");
        pacijent.setPrezime("Lukic");
        pacijent.setEmail("mglukic@gmail.com");
        pacijent.setPassword("88888888");
        pacijent.setGrad("Smederevo");
        pacijent.setDrzava("Srbija");
        pacijent.setAdresa("Adresa");
        pacijent.setTelefon("123456789");
        pacijent.setBroj_osiguranika("123");


        int dbSizeBeforeAdd = pacijentService.findAll().size();

        Pacijent dbStudent = null;
        try {
            dbStudent = pacijentService.create(pacijent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Validate that new student is in the database
        List<Pacijent> pacijenti = (List<Pacijent>) pacijentService.findAll();
        assertEquals(pacijenti.size(), dbSizeBeforeAdd+1);

        dbStudent = pacijenti.get(pacijenti.size() - 1); //get last student
        assertEquals(dbStudent.getId(),4L);
        assertEquals(dbStudent.getIme(),"Milica");
        assertEquals(dbStudent.getPrezime(),"Lukic");
        assertEquals(dbStudent.getPassword(),"88888888");
        assertEquals(dbStudent.getGrad(),"Smederevo");
        assertEquals(dbStudent.getDrzava(),"Srbija");
        assertEquals(dbStudent.getAdresa(),"Adresa");
        assertEquals(dbStudent.getTelefon(),"123456789");
        assertEquals(dbStudent.getBroj_osiguranika(),"123");
    }

    @Test
    @Transactional
    @Rollback(true)
    void update() {

        Pacijent dbPacijent = pacijentService.findById(1L);

        dbPacijent.setIme("Petar");
        dbPacijent.setPrezime("Perovic");
        dbPacijent.setGrad("Smederevo");

        try {
            dbPacijent = pacijentService.create(dbPacijent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //verify that database contains updated data
        dbPacijent = pacijentService.findById(1L);

        assertEquals(dbPacijent.getIme(),"Petar");
        assertEquals(dbPacijent.getPrezime(),"Perovic");
        assertEquals(dbPacijent.getGrad(),"Smederevo");

    }

    @Test
    @Transactional
    @Rollback(true)
    void delete() {
        int dbSizeBeforeRemove = pacijentService.findAll().size();
        pacijentService.delete(3L);

        List<Pacijent> pacijenti = (List<Pacijent>) pacijentService.findAll();
        assertEquals(pacijenti.size(),dbSizeBeforeRemove - 1);


      //  Pacijent dbPacijent = pacijentService.findById(3L);
     //   assertEquals(dbPacijent, null);
    }

    //Negativni testovi

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateManjeOdOsamKaraktera() throws Exception {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Milica");
        pacijent.setPrezime("Lukic");
        pacijent.setEmail("mglukic@gmail.com");
        pacijent.setPassword("888");
        pacijent.setGrad("Smederevo");
        pacijent.setDrzava("Srbija");
        pacijent.setAdresa("Adresa");
        pacijent.setTelefon("123456789");
        pacijent.setBroj_osiguranika("123");

        pacijentService.create(pacijent);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateManjeOdDevetTelefon() throws Exception {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Milica");
        pacijent.setPrezime("Lukic");
        pacijent.setEmail("mglukic@gmail.com");
        pacijent.setPassword("88888888");
        pacijent.setGrad("Smederevo");
        pacijent.setDrzava("Srbija");
        pacijent.setAdresa("Adresa");
        pacijent.setTelefon("123");
        pacijent.setBroj_osiguranika("123");

        pacijentService.create(pacijent);

    }
}