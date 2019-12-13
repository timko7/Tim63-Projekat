package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Sala;
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

    @RequestMapping(method = GET, value = "{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TipPregleda> getTipovePoKlinici(@PathVariable("idKlinike") Long id) {
        return tipPregledaService.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "/tip/{nazivTipa}")
    public TipPregleda loadById(@PathVariable String nazivTipa) {
        return tipPregledaService.findByNaziv(nazivTipa);
    }

    @RequestMapping(method = GET, value = "/tipPoId/{idTipa}")
    public TipPregleda loadByIdTipa(@PathVariable("idTipa") Long idTipa) {
        return tipPregledaService.findById(idTipa);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajTipPregleda(@RequestBody TipPregleda tipPregledaRequest) throws Exception {
        tipPregledaRequest.setNazivTipa(tipPregledaRequest.getNazivTipa().trim());

        Collection<TipPregleda> tipoviUklinici = tipPregledaService.findByIdKlinike(tipPregledaRequest.getIdKlinike());

        for (TipPregleda tipPregleda : tipoviUklinici) {
            if(tipPregleda.getNazivTipa().trim().equals(tipPregledaRequest.getNazivTipa())) {
                return new ResponseEntity<>("Neuspesno dodavanje tipa! Tip sa nazivom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }

        TipPregleda tip = tipPregledaService.create(tipPregledaRequest);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<TipPregleda>(tip, HttpStatus.CREATED);
    }

    /*
     * url: /api/tipoviPregleda/izmeni/{naziv}
     */
    @PutMapping(value = "/izmeni/{naziv}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniTip(@RequestBody TipPregleda tipPregleda, @PathVariable("naziv") String naziv)
            throws Exception {

        Collection<TipPregleda> tipoviUklinici = tipPregledaService.findByIdKlinike(tipPregleda.getIdKlinike());

        for (TipPregleda tipPregleda1 : tipoviUklinici) {
            if(tipPregleda1.getNazivTipa().trim().equals(tipPregleda.getNazivTipa())) {
                return new ResponseEntity<>("Neuspesna izmena tipa pregleda! Tip sa nazivom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }

        TipPregleda tip = tipPregledaService.update(tipPregleda);
        return new ResponseEntity<TipPregleda>(tip, HttpStatus.CREATED);
    }

    /*
     * url: /api/tipoviPregleda/obrisi/{naziv}
     */
    @DeleteMapping(value = "/obrisi/{id}")
    public ResponseEntity<?> izbrisiTipPoID(@PathVariable("id") Long id) {

        tipPregledaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
