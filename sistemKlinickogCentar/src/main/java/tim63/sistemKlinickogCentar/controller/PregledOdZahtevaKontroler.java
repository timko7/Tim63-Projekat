package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.model.PregledOdZahteva;
import tim63.sistemKlinickogCentar.service.KalendarService;
import tim63.sistemKlinickogCentar.service.PregledOdZahtevaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/preglediOdZahteva")
public class PregledOdZahtevaKontroler {

    @Autowired
    private PregledOdZahtevaService pregledOdZahtevaService;

    @Autowired
    private KalendarService kalendarService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<PregledOdZahteva> getPreglede() {
        return pregledOdZahtevaService.findAll();
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajPregled(@RequestBody PregledOdZahteva pregledOdZahteva) throws Exception {
        PregledOdZahteva pregledNew = pregledOdZahtevaService.create(pregledOdZahteva);

        Kalendar kalendar=this.kalendarService.createPrekoPesimistickih(pregledOdZahteva);
        return new ResponseEntity<>(pregledNew, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "/vratiPoLekaru/{idLekara}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<PregledOdZahteva> getPregledPoIDLekra(@PathVariable("idLekara") Long id) {

        return pregledOdZahtevaService.findByIdLekara(id);
    }

    @RequestMapping(method = GET, value = "/vratiPoPacijentu/{idPacijenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<PregledOdZahteva> getPregledPoIDPacijentu(@PathVariable("idPacijenta") Long id) {

        return pregledOdZahtevaService.findByIdPacijenta(id);
    }

    @PutMapping(value = "/odradiPregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> odradiPregled(@RequestBody PregledOdZahteva pregled, @PathVariable("id") Long id)
            throws Exception {

        System.out.println(pregled.getTrajanjePregleda());
        PregledOdZahteva zaIzmenu=pregledOdZahtevaService.findById(id);
        if(zaIzmenu.isOdradjen()==true){
            return new ResponseEntity<>("Paijent vec pregledan", HttpStatus.METHOD_NOT_ALLOWED);
        }
       // pregled.setOdradjen(true);
        PregledOdZahteva p = pregledOdZahtevaService.pregledaj(pregled);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }
    @RequestMapping(method = DELETE,value = "/otkaziPregled/{id}")
    public ResponseEntity<?> otkaziPregled(@PathVariable("id") Long id)
            throws Exception {

      boolean pom=pregledOdZahtevaService.okaziPregled(id);
      if(pom==false){
          return  new ResponseEntity<>("Proslo vreme za otkazivanje", HttpStatus.METHOD_NOT_ALLOWED);
      }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
