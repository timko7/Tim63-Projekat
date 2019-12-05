package tim63.sistemKlinickogCentar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tim63.sistemKlinickogCentar.model.AdminKlinike;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.LoginZahtev;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.service.AdminKlinikeService;
import tim63.sistemKlinickogCentar.service.LekarService;
import tim63.sistemKlinickogCentar.service.PacijentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private PacijentService pacSer;

    @Autowired
    private LekarService lekSer;

    @Autowired
    private AdminKlinikeService adminKlinikeeSer;


    @RequestMapping(method = POST)
    public ResponseEntity<?> login(@RequestBody LoginZahtev zahtev, @Context HttpServletRequest request) {

        Collection<Pacijent> pacijenti = pacSer.findAll();
        Collection<Lekar> lekari = lekSer.findAll();
        Collection<AdminKlinike> adminiKlinike = adminKlinikeeSer.findAll();

        Pacijent p=pacSer.findByEmail(zahtev.getEmail());
        Lekar l=lekSer.findByEmail(zahtev.getEmail());
        AdminKlinike ak=adminKlinikeeSer.findByEmail(zahtev.getEmail());

            if (p!=null) {
                HttpSession session = request.getSession();
                session.setAttribute("pacijent", p);

                return new ResponseEntity<Pacijent>(p, HttpStatus.CREATED);
            }
            else if(l!=null){
                HttpSession session = request.getSession();
                session.setAttribute("lekar", l);

                return new ResponseEntity<Lekar>(l, HttpStatus.CREATED);
            }
            else if(ak!=null){
                HttpSession session = request.getSession();
                session.setAttribute("adminKlinike", ak);

                return new ResponseEntity<AdminKlinike>(ak, HttpStatus.CREATED);
            }

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);



        }

    @RequestMapping(method = GET, value = "/vratiUlogovanog")
    public Object vratiUlogovanog(@Context HttpServletRequest request) {

        HttpSession session = request.getSession();
        Pacijent pacijent = (Pacijent) session.getAttribute("pacijent");
        Lekar lekar = (Lekar) session.getAttribute("lekar");
        AdminKlinike adminKlinike = (AdminKlinike) session.getAttribute("adminKlinike");

        if(pacijent != null)
            return pacijent;

      else if(lekar!=null){
            return  lekar;
        }

        else if(adminKlinike!=null){
           return adminKlinike;
       }
        else
            return null;
    }


    @RequestMapping(method = POST, value = "/logOut")
    public ResponseEntity logOut(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.status(200).build();
    }

}
