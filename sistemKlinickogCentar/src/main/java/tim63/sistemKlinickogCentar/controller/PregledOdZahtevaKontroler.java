package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/preglediOdZahteva")
public class PregledOdZahtevaKontroler {

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<PregledOdZahteva> getPreglede() {
        return pregledOdZahtevaService.findAll();
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajPregled(@RequestBody PregledOdZahteva pregledOdZahteva) throws Exception {
        PregledOdZahteva pregledNew = pregledOdZahtevaService.create(pregledOdZahteva);

        return new ResponseEntity<>(pregledNew, HttpStatus.CREATED);
    }

}
