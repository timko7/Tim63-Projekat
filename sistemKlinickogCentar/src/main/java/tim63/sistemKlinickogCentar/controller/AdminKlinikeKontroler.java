package tim63.sistemKlinickogCentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.service.AdminKlinikeService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/adminiKlinike")
public class AdminKlinikeKontroler {

    @Autowired
    private AdminKlinikeService adminKlinikeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AdminKlinike> getAdmineKlinike() {
        return adminKlinikeService.findAll();
    }

    @RequestMapping(method = GET, value = "/user/{userId}")
    public AdminKlinike loadById(@PathVariable Long userId) {
        return adminKlinikeService.findById(userId);
    }

    @RequestMapping(method = POST, value = "/add")
    public ResponseEntity<?> dodajAdminaKlinike(@RequestBody AdminKlinike adminKlinikeRequest) throws Exception {
        AdminKlinike exist = adminKlinikeService.findByEmail(adminKlinikeRequest.getEmail());
        if (exist != null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        AdminKlinike admin = adminKlinikeService.create(adminKlinikeRequest);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<AdminKlinike>(admin, HttpStatus.CREATED);
    }

    /*
     * url: /api/adminiKlinike/izmeni/{email}
     */
    @PutMapping(value = "/izmeni/{email}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKlinike> izmeniAdminaKlinike(@RequestBody AdminKlinike adminKlinike, @PathVariable String email)
            throws Exception {
        AdminKlinike a = adminKlinikeService.update(adminKlinike);
        return new ResponseEntity<AdminKlinike>(a, HttpStatus.CREATED);
    }

    /*
     * url: /api/adminiKlinike/promeniLozinku/{idAdmina}
     */
    @PutMapping(value = "/promeniLozinku/{idAdmina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKlinike> izmeniPassAdminaKlinike(@RequestBody String noviPassword, @PathVariable("idAdmina") Long idAdmina)
            throws Exception {

        AdminKlinike a = adminKlinikeService.promeniLozinku(idAdmina, noviPassword);
        return new ResponseEntity<AdminKlinike>(a, HttpStatus.OK);
    }

    /*
     * url: /api/adminiKlinike/promeniPodatke/
     */
    @PutMapping(value = "/promeniPodatke/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKlinike> promeniPodatkeAdmina(@RequestBody AdminKlinike adminKlinike)
            throws Exception {

        AdminKlinike a = adminKlinikeService.update(adminKlinike);
        return new ResponseEntity<AdminKlinike>(a, HttpStatus.OK);
    }

    /*
     * url: /api/adminiKlinike/obrisi/{email}
     */
    @DeleteMapping(value = "/obrisi/{email}")
    public ResponseEntity<AdminKlinike> izbrisiAdminKlinike(@PathVariable("email") String email) {
        adminKlinikeService.deleteByEmail(email);
        return new ResponseEntity<AdminKlinike>(HttpStatus.NO_CONTENT);
    }


}
