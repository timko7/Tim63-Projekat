package tim63.sistemKlinickogCentar.controller;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim63.sistemKlinickogCentar.model.*;

import tim63.sistemKlinickogCentar.model.dto.LekarKlinikaDTO;
import tim63.sistemKlinickogCentar.service.KalendarService;
import tim63.sistemKlinickogCentar.service.LekarService;
import tim63.sistemKlinickogCentar.service.OcenaService;
import tim63.sistemKlinickogCentar.service.ZahtevOdsustvoService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/api/lekari")
public class LekarController {

    @Autowired
    private LekarService lekarSer;

    @Autowired
    private KalendarService kalendarService;
    @Autowired
    private ZahtevOdsustvoService odsustvoService;

    @Autowired
    private OcenaService ocenaService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekari() {
        return  this.lekarSer.findAll();
    }

    @RequestMapping(method = GET, value = "{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekarePoKlinici(@PathVariable("idKlinike") Long id) {
        return lekarSer.findByIdKlinike(id);
    }

    @RequestMapping(method = GET, value = "vratiPoTipu/{idTipa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lekar> getLekarePoTipu(@PathVariable("idTipa") Long idTipa) {
        return lekarSer.findByIdTipa(idTipa);
    }

    @RequestMapping(method = POST, value = "/dodajLekara")
    //@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dodajLekara(@RequestBody Lekar lekar) throws Exception {

        Collection<Lekar> lakariUklicini = lekarSer.findByIdKlinike(lekar.getIdKlinike());
        Lekar existUser = this.lekarSer.findByEmail(lekar.getEmail());

        for (Lekar lekar1 : lakariUklicini) {
            if(lekar1.getEmail().equals(lekar.getEmail())) {
                return new ResponseEntity<>("Neuspesno dodavanje lekara! Lekar sa emailom vec postoji u klinici!", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }

        Lekar user = this.lekarSer.create(lekar);
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    /*
     * U viticastim zagradama se navodi promenljivi deo putanje.
     *
     * url: /api/greetings/1 GET
     */
    @RequestMapping(method = GET, value = "/lekar/{userId}")
    public Lekar loadById(@PathVariable("userId") Long userId) {
        return this.lekarSer.findById(userId);
    }

    /*
     * Prilikom poziva metoda potrebno je navesti nekoliko parametara unutar @PostMappimng anotacije:
     * 	url kao vrednost 'value' atributa (ukoliko se izostavi, ruta do metode je ruta do kontrolera),
     * 	u slucaju POST zahteva atribut 'produces' sa naznakom tipa odgovora (u nasem slucaju JSON) i
     * 	atribut consumes' sa naznakom oblika u kojem se salje podatak (u nasem slucaju JSON).
     *
     * Anotiranjem parametra sa @RequestBody Spring ce pokusati od prosledjenog JSON
     * podatka da napravi objekat tipa Greeting.
     *
     * url: /api/greetings POST
     */


    /*
     * url: /api/greetings/1 PUT
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lekar> izmeniLekara(@RequestBody Lekar lekar, @PathVariable Long id)
            throws Exception {
        Lekar p=lekarSer.update(lekar);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PostMapping(value = "oceniLekara/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> oceniLekra(@RequestBody double ocena, @PathVariable("id") Long id)
            throws Exception {

        Ocena o=new Ocena();
        o.setOcena(ocena);
        o.setIdLekara(id);
        o=ocenaService.create(o);
        return new ResponseEntity<>(o, HttpStatus.CREATED);
    }

    @RequestMapping(method = PUT, value = "/dopuniLekara")
    public ResponseEntity<Lekar> dopuniLekara(@RequestBody LekarKlinikaDTO lekarKlinika)
            throws Exception {
        lekarKlinika.toString();
        return null;
    }

    @RequestMapping(method = GET, value = "/srednjaOcena/{id}")
    public double srednjaOcena( @PathVariable("id") Long id)
            throws Exception {
        List<Ocena> oceneLekara=ocenaService.findByIdLekara(id);
        double zbir=0;
        for(Ocena ocena:oceneLekara){
            zbir+=ocena.getOcena();
        }
        return zbir/oceneLekara.size();
    }
    /*
     * url: /api/lekari/promeniLozinku/{idLekara}
     */
    @PutMapping(value = "/promeniLozinku/{idLekara}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> izmeniPassLekara(@RequestBody String noviPassword, @PathVariable("idLekara") Long idLekara)
            throws Exception {

        Lekar a = lekarSer.promeniLozinku(idLekara, noviPassword);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    /*
     * url: /api/greetings/1 DELETE
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lekar> izbrisiLekara(@PathVariable("id") Long id) {
        lekarSer.delete(id);
        return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(method =POST, value = "/pretraziLekarePaVratiKlinike")
    public ArrayList<Lekar> pretrazi( @RequestBody PretragaKlinike zahtev) {

        //LocalDateTime datumZahteva= LocalDateTime.parse(zahtev.getTermin().toString());
      /*  LocalDate d=zahtev.getTermin();
        System.out.println("zahtev vreme "+zahtev.getTermin());
        Collection<Lekar> lekariPoKlinici=lekarSer.findByIdKlinike(zahtev.getIdKlinike());
        System.out.println("lekari klinike"+lekariPoKlinici.size());
        ArrayList<Lekar> lekariPoTipu=new ArrayList<>();
        ArrayList<Lekar> lekariKalendara=new ArrayList<>();

        for(Lekar l:lekariPoKlinici){
            if(l.getIdTipa().equals(zahtev.getIdTipa())){
                lekariPoTipu.add(l);
                System.out.println("lekar "+l.getIme());
            }
        }

        ArrayList<Lekar> bezGodisnjeg=new ArrayList<>();
        for(Lekar l:lekariPoTipu) {
            Collection<ZahtevOdsustvo> odsustva = odsustvoService.findByIdLekara(l.getId());
            if (odsustva.isEmpty()) {
                System.out.println("lekar za vracanje " + l.getIme());
                bezGodisnjeg.add(l);
            } else {
                for (ZahtevOdsustvo oo : odsustva) {
                    System.out.println("od:  " + oo.getDatumPocetka());
                    System.out.println("od:  " + oo.getDatumZavrsetka());
                    System.out.println("zahtev  " + zahtev.getTermin());
                    if (!(d.isAfter(oo.getDatumPocetka()) && d.isBefore(oo.getDatumZavrsetka()))) {
                        System.out.println("lekar za vracanje " + l.getIme());
                        bezGodisnjeg.add(l);
                    }
                }
            }
        }

*/


        return lekarSer.pretrazi(zahtev);
    }
    @RequestMapping(method =POST, value = "/pretraziLekare")
    public ArrayList<Lekar> pretraziLekare( @RequestBody PretragaKlinike zahtev) {

     /*   Collection<Lekar> lekariPoKlinici=lekarSer.findByIdKlinike(zahtev.getIdKlinike());
        System.out.println("lekari klinike"+lekariPoKlinici.size());
        ArrayList<Lekar> lekariPoTipu=new ArrayList<>();
        LocalDate d=zahtev.getTermin();

        for(Lekar l:lekariPoKlinici){
            if(l.getIdTipa().equals(zahtev.getIdTipa())){
                lekariPoTipu.add(l);
                System.out.println("lekar "+l.getIme());
            }
        }


        ArrayList<Lekar> bezGodisnjeg=new ArrayList<>();
        for(Lekar l:lekariPoTipu) {
            Collection<ZahtevOdsustvo> odsustva = odsustvoService.findByIdLekara(l.getId());
            if (odsustva.isEmpty()) {
                System.out.println("lekar za vracanje " + l.getIme());
                bezGodisnjeg.add(l);
            } else {
                for (ZahtevOdsustvo oo : odsustva) {
                    System.out.println("od:  " + oo.getDatumPocetka());
                    System.out.println("od:  " + oo.getDatumZavrsetka());
                    System.out.println("zahtev  " + zahtev.getTermin());
                    if (!(d.isAfter(oo.getDatumPocetka()) && d.isBefore(oo.getDatumZavrsetka()))) {
                        System.out.println("lekar za vracanje " + l.getIme());
                        bezGodisnjeg.add(l);
                    }
                }
            }
        }


        ArrayList<Lekar> zaKraj=new ArrayList<>();
        for(Lekar l:bezGodisnjeg){
            if(zahtev.getIme()!=null){
            if(l.getIme().contains(zahtev.getIme())){
                if(zahtev.getPrezime()!=null){
                    if(l.getPrezime().contains(zahtev.getPrezime())){
                        zaKraj.add(l);
                    }
                }
                else {
                    zaKraj.add(l);
                }
            }

            }else{
                return bezGodisnjeg;
            }
        }
*/

        return lekarSer.pretraziLekre(zahtev);
    }


  /*  public ArrayList<Integer> vratiTermine( Long idLekara) {
        ArrayList<Integer>termini=new ArrayList<Integer>();
        Lekar l=lekarSer.findById(idLekara);
        for(int i=l.getRadnoVremeOd();i<l.getRadnoVremeDo();i++){
            termini.add(i);
        }
        return  termini;
    }
*/
    @RequestMapping(method =GET, value = "/vratiTermine/{idLekara}")
    public ArrayList<Integer> vratiTermineNaFront( @PathVariable("idLekara") Long idLekara,@RequestParam("datum")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum) {


         /*   List<Kalendar> kalendari = kalendarService.findByIdLekara(idLekara);
            List<Kalendar> kalendariPoDatumu =new ArrayList<>();
            ArrayList<Integer> termini=vratiTermine(idLekara);
            ArrayList<Integer> noviTermini=new ArrayList<>();
            if (kalendari.isEmpty()) {
               noviTermini=termini;
            } else {
                for (Kalendar kk : kalendari) {
                    if (kk.getDatum().toLocalDate().equals(datum)) {
                        kalendariPoDatumu.add(kk);
                    }
                }
            }

                if (!kalendariPoDatumu.isEmpty()){
                    System.out.println("ubacujem  " + termini);
                    for(int i=0;i<termini.size();i++){
                        for (Kalendar kk : kalendariPoDatumu) {
                            System.out.println("od:  " + kk.getOd());
                            System.out.println("zahtev  " + termini.get(i));
                            if (kk.getOd().getHour() != termini.get(i)) {
                                System.out.println("ubacujem  " + termini.get(i));
                                noviTermini.add(termini.get(i));
                            }
                        }
                    }
            }*/


            return  lekarSer.vratiTermineNaFront(idLekara,datum);
        }

}
