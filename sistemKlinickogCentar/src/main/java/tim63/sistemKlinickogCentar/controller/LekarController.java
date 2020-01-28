package tim63.sistemKlinickogCentar.controller;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.Ocena;
import tim63.sistemKlinickogCentar.model.PretragaKlinike;
import tim63.sistemKlinickogCentar.model.dto.LekarKlinikaDTO;
import tim63.sistemKlinickogCentar.service.KalendarService;
import tim63.sistemKlinickogCentar.service.LekarService;
import tim63.sistemKlinickogCentar.service.OcenaService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/api/lekari")
public class LekarController {

    @Autowired
    private LekarService lekarSer;

    @Autowired
    private KalendarService kalendarService;
    @Autowired
    private OcenaService ocenaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekari() {
        return  this.lekarSer.findAll();
    }

    @RequestMapping(method = GET, value = "{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekarePoKlinici(@PathVariable("idKlinike") Long id) {
        return lekarSer.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "vratiPoTipu/{idTipa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekarePoTipu(@PathVariable("idTipa") Long idTipa) {
        return lekarSer.findByIdTipa(idTipa);
    }

    @RequestMapping(method = POST, value = "/dodajLekara")
    //@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dodajLekara(@RequestBody Lekar lekar) throws Exception {

        Collection<Lekar> lakariUklicini = lekarSer.findByIdKlinike(lekar.getIdKlinike());
        Lekar existUser = this.lekarSer.findByEmail(lekar.getEmail());

        for (Lekar lekar1 : lakariUklicini) {
            if(lekar1.getEmail().equals(lekar.getEmail())) {
                return new ResponseEntity<>("Neuspesno dodavanje lekara! Lekar sa emailom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }

        Lekar user = this.lekarSer.create(lekar);
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    /*
     * U viticastim zagradama se navodi promenljivi deo putanje.
     *
     * url: /api/greetings/1 GET
     */
    @RequestMapping(method = GET, value = "/lekar/{userId}")
    public Lekar loadById(@PathVariable("userId") Long userId) {
        return this.lekarSer.findById(userId);
    }

    /*
     * Prilikom poziva metoda potrebno je navesti nekoliko parametara unutar @PostMappimng anotacije:
     * 	url kao vrednost 'value' atributa (ukoliko se izostavi, ruta do metode je ruta do kontrolera),
     * 	u slucaju POST zahteva atribut 'produces' sa naznakom tipa odgovora (u nasem slucaju JSON) i
     * 	atribut consumes' sa naznakom oblika u kojem se salje podatak (u nasem slucaju JSON).
     *
     * Anotiranjem parametra sa @RequestBody Spring ce pokusati od prosledjenog JSON
     * podatka da napravi objekat tipa Greeting.
     *
     * url: /api/greetings POST
     */


    /*
     * url: /api/greetings/1 PUT
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lekar> izmeniLekara(@RequestBody Lekar lekar, @PathVariable Long id)
            throws Exception {
        Lekar p=lekarSer.update(lekar);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PostMapping(value = "oceniLekara/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> oceniLekra(@RequestBody double ocena, @PathVariable("id") Long id)
            throws Exception {

        Ocena o=new Ocena();
        o.setOcena(ocena);
        o.setIdLekara(id);
        o=ocenaService.create(o);
        return new ResponseEntity<>(o, HttpStatus.CREATED);
    }

    @RequestMapping(method = PUT, value = "/dopuniLekara")
    public ResponseEntity<Lekar> dopuniLekara(@RequestBody LekarKlinikaDTO lekarKlinika)
            throws Exception {
        lekarKlinika.toString();
        return null;
    }

    @RequestMapping(method = GET, value = "/srednjaOcena/{id}")
    public double srednjaOcena( @PathVariable("id") Long id)
            throws Exception {
        List<Ocena> oceneLekara=ocenaService.findByIdLekara(id);
        double zbir=0;
        for(Ocena ocena:oceneLekara){
            zbir+=ocena.getOcena();
        }
        return zbir/oceneLekara.size();
    }
    /*
     * url: /api/greetings/1 DELETE
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lekar> izbrisiLekara(@PathVariable("id") Long id) {
        lekarSer.delete(id);
        return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(method =POST, value = "/pretraziLekarePaVratiKlinike")
    public ArrayList<Lekar> pretrazi( @RequestBody PretragaKlinike zahtev) {

        System.out.println("zahtev vreme "+zahtev.getTermin());
        Collection<Lekar> lekariPoKlinici=lekarSer.findByIdKlinike(zahtev.getIdKlinike());
        System.out.println("lekari klinike"+lekariPoKlinici.size());
        ArrayList<Lekar> lekariPoTipu=new ArrayList<>();
        ArrayList<Lekar> lekariKalendara=new ArrayList<>();

        for(Lekar l:lekariPoKlinici){
            if(l.getIdTipa().equals(zahtev.getIdTipa())){
                lekariPoTipu.add(l);
                System.out.println("lekar "+l.getIme());
            }
        }

        ArrayList<Lekar> zaVracanje=new ArrayList<>();
        for(Lekar l:lekariPoTipu) {
            List<Kalendar> kalendari = kalendarService.findByIdLekara(l.getId());
            if (kalendari.isEmpty()) {
                System.out.println("lekar za vracanje " + l.getIme());
                zaVracanje.add(l);
            } else {
                for (Kalendar kk : kalendari) {
                    System.out.println("od:  " + kk.getOd());
                    System.out.println("zahtev  " + zahtev.getTermin());
                    if (kk.getOd().toLocalTime().compareTo(zahtev.getTermin().toLocalTime()) != 0) {
                        System.out.println("lekar za vracanje " + l.getIme());
                        zaVracanje.add(l);
                    }
                }
            }
        }

        return zaVracanje;
    }
    @RequestMapping(method =POST, value = "/pretraziLekare")
    public ArrayList<Lekar> pretraziLekare( @RequestBody PretragaKlinike zahtev) {

        Collection<Lekar> lekariPoKlinici=lekarSer.findByIdKlinike(zahtev.getIdKlinike());
        System.out.println("lekari klinike"+lekariPoKlinici.size());
        ArrayList<Lekar> lekariPoTipu=new ArrayList<>();


        for(Lekar l:lekariPoKlinici){
            if(l.getIdTipa().equals(zahtev.getIdTipa())){
                lekariPoTipu.add(l);
                System.out.println("lekar "+l.getIme());
            }
        }

        ArrayList<Lekar> zaVracanje=new ArrayList<>();
        for(Lekar l:lekariPoTipu) {
            List<Kalendar> kalendari = kalendarService.findByIdLekara(l.getId());
            if (kalendari.isEmpty()) {
                zaVracanje.add(l);
            } else {
                for (Kalendar kk : kalendari) {
                    if (kk.getOd().toLocalTime().compareTo(zahtev.getTermin().toLocalTime()) != 0) {
                        System.out.println("lekar za vracanje " + l.getIme());
                        zaVracanje.add(l);
                    }
                }
            }
        }
        ArrayList<Lekar> zaKraj=new ArrayList<>();
        for(Lekar l:zaVracanje){
            if(l.getIme().equals(zahtev.getIme())){
                if(l.getPrezime().equals(zahtev.getPrezime())){
                    zaKraj.add(l);
                }
            }
        }

        return zaKraj;
    }
}
