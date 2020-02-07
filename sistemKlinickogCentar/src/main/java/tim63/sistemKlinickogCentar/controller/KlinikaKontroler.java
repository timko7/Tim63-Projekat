package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Klinika;
import tim63.sistemKlinickogCentar.model.OcenaKlinike;
import tim63.sistemKlinickogCentar.service.KlinikaService;
import tim63.sistemKlinickogCentar.service.OcenaKlinikeService;

import javax.ws.rs.POST;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/klinike")

public class KlinikaKontroler {

    @Autowired
    private KlinikaService klinikaSer;

    @Autowired
    private OcenaKlinikeService ocenaKlinikeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Klinika> getKlinike() {
        return this.klinikaSer.findAll();
    }

    @RequestMapping(method = GET, value = "/{klinikaId}")
    public Klinika loadById(@PathVariable("klinikaId") Long userId) {
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

    @PutMapping(value = "promeniPodatke/{stariNaziv}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniKliniku(@RequestBody Klinika klinika, @PathVariable("stariNaziv") String stariNazivKlinike)
            throws Exception {
        //Klinika klinika1 = klinikaSer.update(klinika);
        Klinika klinika1 = klinikaSer.izmeniPodatke(stariNazivKlinike, klinika);

        if (klinika1 != null) {
            return new ResponseEntity<>(klinika1, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Neuspesna izmena podataka klinike. Moguce da naziv vec postoji!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "oceniKliniku/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> oceniKliniku(@RequestBody double ocena, @PathVariable("id") Long id)
            throws Exception {
        OcenaKlinike o=new OcenaKlinike();
        o.setOcena(ocena);
        o.setIdKlinike(id);
        o=ocenaKlinikeService.create(o);
        return new ResponseEntity<>(o, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "/srednjaOcenaKlinike/{id}")
    public double srednjaOcena( @PathVariable("id") Long id) throws Exception {
        List<OcenaKlinike> oceneLekara=ocenaKlinikeService.findByIdKlinike(id);
        double zbir=0;
        for(OcenaKlinike ocena:oceneLekara){
            zbir+=ocena.getOcena();
        }
        return zbir/oceneLekara.size();
    }

    @RequestMapping(method = GET, value = "/prihod/{idKlinike}/{pocetak}/{kraj}")
    public double getPrihodPoPeriodu( @PathVariable("idKlinike") Long idKlinike, @PathVariable("pocetak") String pocetak, @PathVariable("kraj") String kraj) throws Exception {

        return klinikaSer.getPrihodPoPeriod(idKlinike, pocetak, kraj);
    }


}
