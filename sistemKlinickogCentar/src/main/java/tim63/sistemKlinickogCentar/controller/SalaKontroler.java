package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Sala;
import tim63.sistemKlinickogCentar.model.dto.SalaDatumDTO;
import tim63.sistemKlinickogCentar.service.SalaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/sale")
public class SalaKontroler {

    @Autowired
    private SalaService salaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Sala> getSale() { return salaService.findAll(); }

    @RequestMapping(method = GET, value = "{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Sala> getSalePoKlinici(@PathVariable("idKlinike") Long id) {
        return salaService.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "/sala/{nazivSale}")
    public Sala ucitajPoNazivu(@PathVariable String nazivSale) {
        return salaService.findByNaziv(nazivSale);
    }

    @RequestMapping(method = GET, value = "/uzmiSalu/{idSale}")
    public Sala ucitajPoId(@PathVariable("idSale") Long idSale) {
        return salaService.findById(idSale);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajSalu(@RequestBody Sala sala) throws Exception {
        sala.setNaziv(sala.getNaziv().trim());

        Collection<Sala> saleUklinici = salaService.findByIdKlinike(sala.getIdKlinike());

        for (Sala sala1 : saleUklinici) {
            if(sala1.getNaziv().trim().equals(sala.getNaziv())) {
                return new ResponseEntity<>("Neuspesno dodavanje sale! Sala sa nazivom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }
        System.out.println("TEST sala: " + sala.getNaziv());
        /*if (exist != null) {
            return new ResponseEntity<>("Sala sa nazivom vec postoji!", HttpStatus.METHOD_NOT_ALLOWED);
        }*/

        Sala salaNew = salaService.create(sala);

        return new ResponseEntity<>(salaNew, HttpStatus.CREATED);
    }

    /*
     * url: /api/sale/izmeni/{naziv}
     */
    @PutMapping(value = "/izmeni/{naziv}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniSalu(@RequestBody Sala sala, @PathVariable("naziv") String naziv)
            throws Exception {

        Sala salaZaIzmenu = salaService.findById(sala.getId());
        Collection<Sala> saleUklinici = salaService.findByIdKlinike(sala.getIdKlinike());

        for (Sala sala1 : saleUklinici) {
            if(sala1.getNaziv().trim().equals(sala.getNaziv())) {
                return new ResponseEntity<>("Neuspesna izmena sale! Sala sa nazivom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }

        Sala sala1 = salaService.update(sala);
        return new ResponseEntity<>(sala1, HttpStatus.CREATED);
    }

    /*
     * url: /api/sale/pretrazi/{idKlinike}/{naziv}/{datum}
     */

    @RequestMapping(method = GET, value = "/pretrazi/{idKlinike}/{naziv}/{datum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Sala> pretraziSaleNavivDatum(@PathVariable("idKlinike") Long idKlinike, @PathVariable("naziv") String nazivSale, @PathVariable("datum") String datum) {
        //return salaService.findByIdKlinike(id);
        return salaService.pretraziPoNazivuDatumuKlinike(idKlinike, nazivSale, datum);
    }

    /*
     * url: /api/sale/prvaSlobodna/{idKlinike}/{datum}
     */

    @RequestMapping(method = GET, value = "/prvaSlobodna/{idKlinike}/{datum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaDatumDTO getPrviSledeciSlobodanTermin(@PathVariable("idKlinike") Long idKlinike, @PathVariable("datum") String datum) {
        //return salaService.findByIdKlinike(id);
        //return salaService.pretraziPoNazivuDatumuKlinike(idKlinike, nazivSale, datum);
        return salaService.getPrviSledeciSlobodanTermin(idKlinike, datum);
    }





    /*
     * url: /api/sale/obrisi/{naziv}
     */
    @DeleteMapping(value = "/obrisi/{id}")
    public ResponseEntity<?> izbiriSaluPoID(@PathVariable("id") Long id) {

        salaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
