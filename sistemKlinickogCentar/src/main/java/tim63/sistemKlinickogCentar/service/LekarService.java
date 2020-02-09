package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.*;
import tim63.sistemKlinickogCentar.repository.LekarRepositoryInterface;

import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LekarService implements LekarInterface {

    @Autowired
    public LekarRepositoryInterface lekRep;

    @Autowired
    private ZahtevOdsustvoService odsustvoService;

    @Autowired
    private KalendarService kalendarService;

    @Override
    public Collection<Lekar> findAll() {
        Collection<Lekar> result = lekRep.findAll();
        return result;
    }

    @Override
    public Lekar findById(Long id) {
        Lekar u = lekRep.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public Collection<Lekar> findByIdKlinike(Long idKlinike) {
        return lekRep.findByIdKlinike(idKlinike);
    }

    @Override
    public Collection<Lekar> findByIdTipa(Long idTipa) {
        return lekRep.findByIdTipa(idTipa);
    }

    @Override
    public ArrayList<Lekar> pretrazi(PretragaKlinike zahtev) {
        if(zahtev.getTermin()==null || zahtev.getIdTipa()==null){
            throw new DataIntegrityViolationException("Ne moze bez datuma/IdTipa");
        }
        LocalDate d = zahtev.getTermin();
        System.out.println("zahtev vreme " + zahtev.getTermin());
        Collection<Lekar> lekariPoKlinici = this.findByIdKlinike(zahtev.getIdKlinike());
        System.out.println("lekari klinike" + lekariPoKlinici.size());
        ArrayList<Lekar> lekariPoTipu = new ArrayList<>();
        ArrayList<Lekar> lekariKalendara = new ArrayList<>();

        for (Lekar l : lekariPoKlinici) {
            if (l.getIdTipa().equals(zahtev.getIdTipa())) {
                lekariPoTipu.add(l);
                System.out.println("lekar " + l.getIme());
            }
        }

        ArrayList<Lekar> bezGodisnjeg = new ArrayList<>();
        for (Lekar l : lekariPoTipu) {
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
        return  bezGodisnjeg;
    }

    @Override
    public ArrayList<Lekar> pretraziLekre(PretragaKlinike zahtev) {

        if(zahtev.getTermin()==null || zahtev.getIdTipa()==null){
            throw new DataIntegrityViolationException("Ne moze bez datuma/IdTipa");
        }
        Collection<Lekar> lekariPoKlinici=this.findByIdKlinike(zahtev.getIdKlinike());

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
        return  zaKraj;
    }

    @Override
    public ArrayList<Integer> vratiTermineNaFront(Long idLekara, LocalDate datum) {



        if(datum==null || idLekara==null){
            throw new DataIntegrityViolationException("Ne moze bez datuma/IdLekara");
        }
        LocalDateTime datumVremeSada = LocalDateTime.now();

        int compareValue = datum.compareTo(datumVremeSada.toLocalDate());

        if (compareValue < 0) {
            return null;
        }
        List<Kalendar> kalendari = kalendarService.findByIdLekara(idLekara);
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
                    if (kk.getOd().getHour() == termini.get(i)) {
                        System.out.println("izbacujem  " + termini.get(i));
                        termini.remove(termini.get(i));
                    }

                }
            }
        }
        noviTermini=termini;
        return noviTermini;
    }

    @Override
    public ArrayList<Integer> vratiTermine(Long idLekara) {
        ArrayList<Integer>termini=new ArrayList<Integer>();
        Lekar l=this.findById(idLekara);
        for(int i=l.getRadnoVremeOd();i<l.getRadnoVremeDo();i++){
            termini.add(i);
        }
        return  termini;
    }

    @Override
    public Lekar findByEmail(String username) {
        Lekar u = lekRep.findByEmail(username);
        return u;
    }

    @Override
    public Lekar create(Lekar lekar) throws Exception {

        Lekar ret = new Lekar();
        lekar.setSlobodan(true);
        lekar.setPrviPutLogovan(true);
        ret.copyValues(lekar);
        ret = lekRep.save(ret);
        return ret;
    }

    @Override
    public Lekar update(Lekar lekar) throws Exception {

        Lekar lekarZaIzmenu = findById(lekar.getId());
        lekarZaIzmenu.copyValues(lekar);
        lekarZaIzmenu = lekRep.save(lekarZaIzmenu);
        return lekarZaIzmenu;
    }

   /* @Override
    public Lekar dobuniLekara(Lekar lekar, Klinika klinika) throws Exception {
        Lekar l = findByEmail(lekar.getEmail());
        if (l.getKlinika() == null) {
            l.setKlinika(klinika);
        }
        l = this.lekRep.save(l);
        return l;
    }*/

    @Override
    public Lekar promeniLozinku(Long idLekara, String noviPassword) throws Exception {
        Lekar lekarZaPromenu = findById(idLekara);
        lekarZaPromenu.setPassword(noviPassword);
        lekarZaPromenu.setPrviPutLogovan(false);
        lekarZaPromenu = lekRep.save(lekarZaPromenu);
        return lekarZaPromenu;
    }


    @Override
    public void delete(Long id) {
        lekRep.deleteById(id);
    }
}
