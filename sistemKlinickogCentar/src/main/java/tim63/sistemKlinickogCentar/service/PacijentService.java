package tim63.sistemKlinickogCentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.repository.PacijentRepositoryInterface;

import java.util.Collection;

@Service
public class PacijentService implements PacijentInterface {

    @Autowired
    public PacijentRepositoryInterface pacRep;


    @Override
    public Collection<Pacijent> findAll() {
        return pacRep.findAll();
    }

    @Override
    public Pacijent findById(Long id) {
        Pacijent u = pacRep.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public Pacijent findByEmail(String username) {
        Pacijent u = pacRep.findByEmail(username);
        return u;
    }

    @Override
    public Pacijent create(Pacijent pacijent) throws Exception {

        Pacijent u = new Pacijent();

        if(pacijent.getIme()==null){
            return  null;
        }
        else
            u.setIme(pacijent.getIme());

        if(pacijent.getPrezime()==null){
            return  null;
        }
        else
            u.setPrezime(pacijent.getPrezime());

        if(pacijent.getEmail()==null){
            return  null;
        }
        else
            u.setEmail(pacijent.getEmail());

        if(pacijent.getPassword()==null){
            return  null;
        }
        else{
            if(pacijent.getPassword().length()<8){
                return null;
            }else
                u.setPassword(pacijent.getPassword());
        }


        if(pacijent.getGrad()==null){
            return  null;
        }
        else
            u.setGrad(pacijent.getGrad());

        if(pacijent.getDrzava()==null){
            return  null;
        }
        else
            u.setDrzava(pacijent.getDrzava());

        if(pacijent.getAdresa()==null){
            return  null;
        }
        else
            u.setAdresa(pacijent.getAdresa());

        if(pacijent.getTelefon()==null){
            return  null;
        }
        else{
            //if(!pacijent.getTelefon().contains("[0-9]+")){
             //   return  null;

           // }
            // else{
                if(pacijent.getTelefon().length()<9){
                    return null;
                }
                else
                    u.setTelefon(pacijent.getTelefon());
            }
       //}


        if(pacijent.getBroj_osiguranika()==null){
            return  null;
        }
        else
            u.setBroj_osiguranika(pacijent.getBroj_osiguranika());

        u = this.pacRep.save(u);
        return u;
    }

    @Override
    public Pacijent update(Pacijent pacijent) throws Exception {

        Pacijent pacijentZaIzmenu = findById(pacijent.getId());
        pacijentZaIzmenu.copyValues(pacijent);
        pacijentZaIzmenu = pacRep.save(pacijentZaIzmenu);
        return pacijentZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        pacRep.deleteById(id);
    }
}
