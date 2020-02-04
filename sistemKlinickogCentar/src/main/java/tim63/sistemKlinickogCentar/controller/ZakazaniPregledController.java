package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.ZaktaniPregledi;
import tim63.sistemKlinickogCentar.service.KalendarService;
import tim63.sistemKlinickogCentar.service.ZakazaniPregledService;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/zakazaniPregledi")
public class ZakazaniPregledController {

    @Autowired
    private ZakazaniPregledService zp;

    @Autowired
    private KalendarService kalendarService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZaktaniPregledi> getPreglede() {
        return zp.findAll();
    }

    @RequestMapping(method = GET, value = "/{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ZaktaniPregledi getPregledPoID(@PathVariable("idPregleda") Long id) {
        return zp.findById(id);
    }

    @RequestMapping(method = GET, value = "/uzmiZakazane/{idLekara}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZaktaniPregledi> getPregledPoIDLekra(@PathVariable("idLekara") Long id) {

        return zp.findByIdLekara(id);
    }

    @RequestMapping(method = GET, value = "/uzmiZakazanePoKlinici/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZaktaniPregledi> getPregledPoIDKlinike(@PathVariable("idKlinike") Long id) {

        return zp.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "/uzmiZakazanePacijente/{idPacijenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZaktaniPregledi> getPregledPoIDPacijenta(@PathVariable("idPacijenta") Long id) {

        return zp.findByIdPacijenta(id);
    }



    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajPregled(@RequestBody ZaktaniPregledi pregled) throws Exception {


        if(pregled.getId()!=null){
            return new ResponseEntity<>("Pregled vec rezervisan", HttpStatus.METHOD_NOT_ALLOWED);
        }
        int trajanje = pregled.getTrajanjePregleda();
        LocalDateTime datumVreme = pregled.getDatumVreme();
        double cena = pregled.getCena();


        if(pregled.isRezervisan()==true){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

       /* if (trajanje < 1) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Trajanje pregleda je manje od 1!", HttpStatus.METHOD_NOT_ALLOWED);
        }*/

        if (cena < 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Cena je manja 0!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        LocalDateTime datumVremeSada = LocalDateTime.now();

        int compareValue = pregled.getDatumVreme().compareTo(datumVremeSada);

        if (compareValue < 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Datum za dodati je u proslosti!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        ZaktaniPregledi pregledNew = this.zp.create(pregled);


        System.out.println("Datum vreme za dodati: " + pregledNew.getDatumVreme());

        return new ResponseEntity<>(pregledNew, HttpStatus.CREATED);
    }

    /*
     * url: /api/pregledi/obrisi/{id}
     */
    @DeleteMapping(value = "/obrisi/{id}")
    public ResponseEntity<?> izbiriPregledPoID(@PathVariable("id") Long id) {
        zp.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "zakaziPregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ZaktaniPregledi> izmeniOdradjen(@RequestBody ZaktaniPregledi pregled, @PathVariable Long id)
            throws Exception {
        ZaktaniPregledi p = zp.update(pregled);
        return new ResponseEntity<ZaktaniPregledi>(p, HttpStatus.CREATED);
    }

    @PutMapping(value = "/odradiZahtev/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> odradiZahtev(@RequestBody ZaktaniPregledi pregled, @PathVariable Long id)
            throws Exception {

        ZaktaniPregledi p = zp.odradi(id);
        return new ResponseEntity<ZaktaniPregledi>(p, HttpStatus.CREATED);
    }

}
