package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tim63.sistemKlinickogCentar.model.Pacijent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.service.PacijentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/pacijenti")
public class PacijentController {


    @Autowired
    private PacijentService pacijentSer;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection <Pacijent> getPacijenti() {
        return  this.pacijentSer.findAll();
    }

    /*
     * U viticastim zagradama se navodi promenljivi deo putanje.
     *
     * url: /api/greetings/1 GET
     */
    @RequestMapping(method = GET, value = "/user/{userId}")
    public Pacijent loadById(@PathVariable Long userId) {
        return this.pacijentSer.findById(userId);
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
    @RequestMapping(method = POST, value = "/signup")
    public ResponseEntity<?> addUser(@RequestBody Pacijent userRequest) throws Exception {

        Pacijent existUser = this.pacijentSer.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pacijent user = this.pacijentSer.create(userRequest);
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Pacijent>(user, HttpStatus.CREATED);
    }

    /*
     * url: /api/greetings/1 PUT
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> izmeniPacijenta(@RequestBody Pacijent pacijent, @PathVariable Long id)
            throws Exception {
        Pacijent p=pacijentSer.update(pacijent);
        return new ResponseEntity<Pacijent>(p, HttpStatus.CREATED);
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
