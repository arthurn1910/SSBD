package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;


@Stateful
public class MOSEndpoint implements MOSEndpointLocal {
    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    
    @Override
    public void dodajSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.create(spotkanie);
    }

}
