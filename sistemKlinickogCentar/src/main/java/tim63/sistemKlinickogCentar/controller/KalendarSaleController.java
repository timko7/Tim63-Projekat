package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.KalendarSale;
import tim63.sistemKlinickogCentar.service.KalendarSaleService;

import java.util.Collection;

@RestController
@RequestMapping("/api/kalendarSale")
public class KalendarSaleController {

    @Autowired
    private KalendarSaleService kalendarSaleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<KalendarSale> getCeoKalendar() {
        return kalendarSaleService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPoIdSale/{idSale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<KalendarSale> getKalendarSalePoIdSale(@PathVariable("idSale") Long idSale) {
        return kalendarSaleService.findByIdSale(idSale);
    }
}
