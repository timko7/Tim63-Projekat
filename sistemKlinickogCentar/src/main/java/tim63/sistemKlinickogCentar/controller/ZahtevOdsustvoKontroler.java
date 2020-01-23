package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.ZahtevOdsustvo;
import tim63.sistemKlinickogCentar.model.dto.OdbijanjeZahtevaOdsustvoDTO;
import tim63.sistemKlinickogCentar.service.ZahtevOdsustvoService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/zahteviOdsustvo")
public class ZahtevOdsustvoKontroler {

    @Autowired
    private ZahtevOdsustvoService zahtevOdsustvoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZahtevOdsustvo> getSveZashteve() {
        return zahtevOdsustvoService.findAll();
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> posaljiZahtev(@RequestBody ZahtevOdsustvo zahtevOdsustvo) throws Exception {

        ZahtevOdsustvo zahtevNew = zahtevOdsustvoService.posaljiZahtev(zahtevOdsustvo);

        return new ResponseEntity<>(zahtevNew, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "/getPoIDklinike/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ZahtevOdsustvo> getPregledPoIDKlinike(@PathVariable("idKlinike") Long id) {

        return zahtevOdsustvoService.findByIdKlinike(id);
    }

    //api/zahteviOdsustvo/odobri/
    @PutMapping(value = "/odobri/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> odobriZahtev(@RequestBody ZahtevOdsustvo zahtevOdsustvo, @PathVariable("id") Long id) throws IOException, MessagingException {
        ZahtevOdsustvo odobrenZahtev = null;
        odobrenZahtev = zahtevOdsustvoService.odobriZahtev(zahtevOdsustvo);

        return new ResponseEntity<>(odobrenZahtev, HttpStatus.OK);
    }

    //api/zahteviOdsustvo/odbi/
    @PutMapping(value = "/odbi/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> odbiZahtev(@RequestBody OdbijanjeZahtevaOdsustvoDTO odbijanjeZahtevaOdsustvoDTO, @PathVariable("id") Long id) {
        ZahtevOdsustvo odbijenZahtev = null;
        odbijenZahtev = zahtevOdsustvoService.odbiZahtev(odbijanjeZahtevaOdsustvoDTO.getZahtevOdsustvo(), odbijanjeZahtevaOdsustvoDTO.getRazlogOdbijanja());

        return new ResponseEntity<>(odbijenZahtev, HttpStatus.OK);
    }



}
