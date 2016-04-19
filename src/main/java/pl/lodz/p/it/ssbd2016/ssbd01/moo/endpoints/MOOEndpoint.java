package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.OgloszenieFacadeLocal;


@Stateful
public class MOOEndpoint implements MOOEndpointLocal {
    @EJB
    private OgloszenieFacadeLocal ogloszenieFacade;
    
    @Override
    public Ogloszenie pobierzPierwszeOgloszenie() {
        List<Ogloszenie> ogloszenia = ogloszenieFacade.findAll();
        return ogloszenia.get(0);
    }

}
