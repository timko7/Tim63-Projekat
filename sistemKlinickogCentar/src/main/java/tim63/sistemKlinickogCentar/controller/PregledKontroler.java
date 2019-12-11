package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.service.PregledService;

import java.util.Collection;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/pregledi")
public class PregledKontroler {

    @Autowired
    private PregledService pregledService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Pregled> getPreglede() {
        return pregledService.findAll();
    }

    @RequestMapping(method = GET, value = "{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pregled getPregledPoID(@PathVariable("idPregleda") Long id) {
        return pregledService.findById(id);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajPregled(@RequestBody Pregled pregled) throws Exception {
        int trajanje = pregled.getTrajanjePregleda();
        Date datumVreme = pregled.getDatumVreme();
        double cena = pregled.getCena();

        if (trajanje == 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Trajanje pregleda je 0!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (cena < 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Cena je manja 0!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        Pregled pregledNew = pregledService.create(pregled);

        return new ResponseEntity<>(pregledNew, HttpStatus.CREATED);
    } // TODO: napravi jos i proveru da ne sme datum da bude u proslosti. Uzmi trenutni date i proveri dal je manje,vece sta vec

    /*
     * url: /api/pregledi/obrisi/{id}
     */
    @DeleteMapping(value = "/obrisi/{id}")
    public ResponseEntity<?> izbiriPregledPoID(@PathVariable("id") Long id) {
        pregledService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
