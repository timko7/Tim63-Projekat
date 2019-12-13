package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.dto.LekarKlinikaDTO;
import tim63.sistemKlinickogCentar.service.LekarService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/api/lekari")
public class LekarController {

    @Autowired
    private LekarService lekarSer;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekari() {
        return  this.lekarSer.findAll();
    }

    @RequestMapping(method = GET, value = "{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekarePoKlinici(@PathVariable("idKlinike") Long id) {
        return lekarSer.findByIdKlinike(id);
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

    @RequestMapping(method = PUT, value = "/dopuniLekara")
    public ResponseEntity<Lekar> dopuniLekara(@RequestBody LekarKlinikaDTO lekarKlinika)
            throws Exception {
        lekarKlinika.toString();
        return null;
    }

    /*
     * url: /api/greetings/1 DELETE
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lekar> izbrisiLekara(@PathVariable("id") Long id) {
        lekarSer.delete(id);
        return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
    }
}
