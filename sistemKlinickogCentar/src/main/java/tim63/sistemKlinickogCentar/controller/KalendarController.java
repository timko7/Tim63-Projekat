package tim63.sistemKlinickogCentar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.Kalendar;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.service.KalendarService;

import java.util.Collection;

@RestController
@RequestMapping("/api/kalendar")
public class KalendarController {

    @Autowired
    private KalendarService kalendarService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Kalendar> getKalendare() {
        return kalendarService.findAll();
    }

  /*  @GetMapping(value = "kalendariPoPacijentu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Kalendar> getKalendarePoPcijentu(@PathVariable("id") Long id) {
        return kalendarService.findByIdPacijenta(id);
    }*/

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lekar> izbrisiLekara(@PathVariable("id") Long id) {
        kalendarService.delete(id);
        return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
    }}
