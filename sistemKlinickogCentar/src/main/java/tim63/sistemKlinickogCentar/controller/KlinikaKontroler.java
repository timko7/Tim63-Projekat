package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.service.KlinikaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/klinike")

public class KlinikaKontroler {

    @Autowired
    private KlinikaService klinikaSer;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Klinika> getKlinike() {
        return  this.klinikaSer.findAll();
    }

    @RequestMapping(method = GET, value = "/{klinikaId}")
    public Klinika loadById(@PathVariable Long userId) {
        return this.klinikaSer.findById(userId);
    }

    @RequestMapping(method = POST, value = "/napraviKliniku")
    public ResponseEntity<?> napraviKliniku(@RequestBody Klinika userRequest) throws Exception {

        Klinika existUser = this.klinikaSer.findByIme(userRequest.getIme());
        if (existUser != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Klinika user = this.klinikaSer.create(userRequest);
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Klinika>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Klinika> izbrisiKliniku(@PathVariable("id") Long id) {
        klinikaSer.delete(id);
        return new ResponseEntity<Klinika>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klinika> izmeniKliniku(@RequestBody Klinika klinika, @PathVariable Long id)
            throws Exception {
        System.out.println("TEST ID:" + id);
        //System.out.println("Klinika:" + klinika.toString());
        Klinika klinika1 = klinikaSer.update(klinika);
        return new ResponseEntity<Klinika>(klinika1, HttpStatus.CREATED);
    }


}
