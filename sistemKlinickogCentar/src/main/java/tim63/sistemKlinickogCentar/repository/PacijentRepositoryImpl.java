package tim63.sistemKlinickogCentar.repository;

import tim63.sistemKlinickogCentar.model.Pacijent;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PacijentRepositoryImpl implements PacijentRepositoryInterface {

    private static AtomicLong counter = new AtomicLong();

    private HashMap<Long,Pacijent> pacijenti=new HashMap<Long, Pacijent>();
    @Override
    public HashMap<Long,Pacijent> findAll() {
        return (HashMap<Long, Pacijent>) this.pacijenti.values();
    }

    @Override
    public Pacijent findOne(Long id) {
        return this.pacijenti.get(id);
    }

    @Override
    public Pacijent create(Pacijent pacijent) throws Exception {
        Long id=pacijent.getId();
        if(id==null){
            id=counter.incrementAndGet();
            pacijent.setId(id);

        }
        this.pacijenti.put(id,pacijent);
        return pacijent;

    }

    @Override
    public Pacijent update(Pacijent pacijent) throws Exception {
        Long id=pacijent.getId();
        if(id!=null){
            this.pacijenti.put(id,pacijent);
        }
        return pacijent;
    }

    @Override
    public void delete(Long id) {
        pacijenti.remove(id);
    }
}
