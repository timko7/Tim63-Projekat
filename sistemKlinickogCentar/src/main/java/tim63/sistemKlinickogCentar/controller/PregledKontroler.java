package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.*;
import tim63.sistemKlinickogCentar.service.*;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/pregledi")
public class PregledKontroler {

    @Autowired
    private PregledService pregledService;

    @Autowired
    private KalendarService kalendarService;

    @Autowired
    private KalendarSaleService kalendarSaleService;

    @Autowired
    private ZahtevOdsustvoService zahtevOdsustvoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Pregled> getPreglede() {
        return pregledService.findAll();
    }

    @RequestMapping(method = GET, value = "{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pregled getPregledPoID(@PathVariable("idPregleda") Long id) {
        return pregledService.findById(id);
    }

    @RequestMapping(method = GET, value = "uzmiPreglede/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Pregled> getPregledPoIDKlinike(@PathVariable("idKlinike") Long id) {

        return pregledService.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "vratiPoTipu/{idTipa}")
    public Collection<Pregled> nadjiPoTipu(@PathVariable("idTipa") Long idTipa) {
        return this.pregledService.findByIdTipa(idTipa);
    }

    @RequestMapping(method = GET, value = "/vratiPoLekaru/{idLekara}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Pregled> getPregledPoIDLekra(@PathVariable("idLekara") Long id) {

        return pregledService.findByIdLekara(id);
    }
    @RequestMapping(method = GET, value = "/vratiPoPacijentu/{idPacijenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Pregled> getPregledPoIDPacijenta(@PathVariable("idPacijenta") Long id) {

        return pregledService.findByIdPacijenta(id);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajPregled(@RequestBody Pregled pregled) throws Exception {
        /*int trajanje = pregled.getTrajanjePregleda();
        LocalDateTime datumVreme = pregled.getDatumVreme();
        double cena = pregled.getCena();

        if (trajanje < 1) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Trajanje pregleda je manje od 1!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (cena < 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Cena je manja 0!", HttpStatus.METHOD_NOT_ALLOWED);
        }

        LocalDateTime datumVremeSada = LocalDateTime.now();

        int compareValue = pregled.getDatumVreme().compareTo(datumVremeSada);

        if (compareValue < 0) {
            return new ResponseEntity<>("Neuspesno dodavanje pregleda! Datum za dodati je u proslosti!", HttpStatus.METHOD_NOT_ALLOWED);
        }

*/


        Collection<ZahtevOdsustvo> zahteviLekra = zahtevOdsustvoService.findByIdLekara(pregled.getIdLekara());

        if (!zahteviLekra.isEmpty()) {
            for (ZahtevOdsustvo zahtevOdsustvo : zahteviLekra) {
                if (pregled.getDatumVreme().isAfter(zahtevOdsustvo.getDatumPocetka().atStartOfDay())
                        && pregled.getDatumVreme().isBefore(zahtevOdsustvo.getDatumZavrsetka().atStartOfDay())) {
                    return new ResponseEntity<>("Nije moguce napraviti predefinisani pregled. Lekar je na odsustvu za odabrani datum.", HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
        }


        Pregled pregledNew = pregledService.create(pregled);
        if(pregled==null){
            return new ResponseEntity<>("Neuspesno dodavanje pregleda!!", HttpStatus.METHOD_NOT_ALLOWED);
        }


        //System.out.println("Datum vreme za dodati: " + pregledNew.getDatumVreme());

        return new ResponseEntity<>(pregledNew, HttpStatus.CREATED);
    }

    /*
     * url: /api/pregledi/obrisi/{id}
     */
    @DeleteMapping(value = "/obrisi/{id}")
    public ResponseEntity<?> izbiriPregledPoID(@PathVariable("id") Long id) {
        pregledService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "zakaziPregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniRezervisan(@RequestBody Pregled pregled, @PathVariable("id") Long id)
            throws Exception {

        Pregled zaIzmenu=pregledService.findById(id);

        if(zaIzmenu.isRezervisan()==true){
            return new ResponseEntity<>("Pregled vec rezervisan", HttpStatus.METHOD_NOT_ALLOWED);
        }
        pregled.setRezervisan(true);
        Pregled p = pregledService.update(pregled);
        Kalendar k=kalendarService.create(p);
        KalendarSale ks = kalendarSaleService.createPoPredefinisanomPregledu(p);
        return new ResponseEntity<Pregled>(p, HttpStatus.CREATED);
    }

    @PutMapping(value = "odradiPregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniOdradjen(@RequestBody Pregled pregled, @PathVariable("id") Long id)
            throws Exception {

        Pregled zaIzmenu=pregledService.findById(id);
        if(zaIzmenu.isOdradjen()==true){
            return new ResponseEntity<>("Paijent vec pregledan", HttpStatus.METHOD_NOT_ALLOWED);
        }
        pregled.setOdradjen(true);
        Pregled p = pregledService.update(pregled);
        return new ResponseEntity<Pregled>(p, HttpStatus.CREATED);
    }
}
