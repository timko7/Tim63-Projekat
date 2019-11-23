package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.TipPregleda;
import tim63.sistemKlinickogCentar.service.TipPregledaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/tipoviPregleda")
public class TipPregledaKontroler {

    @Autowired
    private TipPregledaService tipPregledaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TipPregleda> getTipove() {
        return tipPregledaService.findAll();
    }

    @RequestMapping(method = GET, value = "/tip/{nazivTipa}")
    public TipPregleda loadById(@PathVariable String nazivTipa) {
        return tipPregledaService.findByNaziv(nazivTipa);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajTipPregleda(@RequestBody TipPregleda tipPregledaRequest) throws Exception {
        TipPregleda exist = tipPregledaService.findByNaziv(tipPregledaRequest.getNazivTipa());
        System.out.println("TEST tip: " + tipPregledaRequest.getNazivTipa());
        if (exist != null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        TipPregleda tip = tipPregledaService.create(tipPregledaRequest);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<TipPregleda>(tip, HttpStatus.CREATED);
    }

    /*
     * url: /api/tipoviPregleda/izmeni/{naziv}
     */
    @PutMapping(value = "/izmeni/{naziv}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TipPregleda> izmeniTip(@RequestBody TipPregleda tipPregleda, @PathVariable("naziv") String naziv)
            throws Exception {
        TipPregleda tip = tipPregledaService.update(tipPregleda);
        return new ResponseEntity<TipPregleda>(tip, HttpStatus.CREATED);
    }

    /*
     * url: /api/tipoviPregleda/obrisi/{naziv}
     */
    @DeleteMapping(value = "/obrisi/{naziv}")
    public ResponseEntity<TipPregleda> izbrisiAdminKlinike(@PathVariable("naziv") String naziv) {
        tipPregledaService.deleteByNaziv(naziv);
        return new ResponseEntity<TipPregleda>(HttpStatus.NO_CONTENT);
    }


}
