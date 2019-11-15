package tim63.sistemKlinickogCentar.controller;

import tim63.sistemKlinickogCentar.model.Pacijent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.service.PacijentService;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/pacijenti")
public class PacijentController {

    private PacijentService pacijentSer;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Pacijent>> getPacijenti() {
       // Collection<Pacijent> greetings = pacijentSer.findAll();
        return new ResponseEntity<>(new ArrayList<>() {{
            add(new Pacijent("Pacijent1", "Pacijent1", null, null,
                    null, null, null, null, 0));
        }}, HttpStatus.OK);
    }

    /*
     * U viticastim zagradama se navodi promenljivi deo putanje.
     *
     * url: /api/greetings/1 GET
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> getPacijenta(@PathVariable("id") Long id) {
        Pacijent pacijent = pacijentSer.findOne(id);

        if (pacijent == null) {
            return new ResponseEntity<Pacijent>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pacijent>(pacijent, HttpStatus.OK);
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> napraviPacijenta(@RequestBody Pacijent pacijent) throws Exception {
        Pacijent sacuvanPacijent = pacijentSer.create(pacijent);
        return new ResponseEntity<Pacijent>(sacuvanPacijent, HttpStatus.CREATED);
    }

    /*
     * url: /api/greetings/1 PUT
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> izmeniPacijenta(@RequestBody Pacijent pacijent, @PathVariable Long id)
            throws Exception {
        Pacijent pacijentZaIzmenu = pacijentSer.findOne(id);
        pacijentZaIzmenu.copyValues(pacijent);

        Pacijent izmenjeniPacijent = pacijentSer.update(pacijentZaIzmenu);

        if (izmenjeniPacijent == null) {
            return new ResponseEntity<Pacijent>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Pacijent>(izmenjeniPacijent, HttpStatus.OK);
    }

    /*
     * url: /api/greetings/1 DELETE
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pacijent> izbrisiPacijenta(@PathVariable("id") Long id) {
        pacijentSer.delete(id);
        return new ResponseEntity<Pacijent>(HttpStatus.NO_CONTENT);
    }
}
