package pl.lodz.p.it.ssbd2016.ssbd01.mos.managers;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;

/**
 * Menadżer dla spotkań
 */
@Stateless
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SpotkanieManager implements SpotkanieManagerLocal {

    @EJB
    SpotkanieFacadeLocal spotkanieFacade;

    @Override
    @RolesAllowed("pobierzSpotkaniaDlaOgloszenia")
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {
        return null;
    }
    
    @Override
    @RolesAllowed("edytujSpotkania")
    public void edytujSwojeSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.edit(spotkanie);
    }
    
    @Override
    @RolesAllowed("rezerwujSpotkanie")
    public void rezerwujSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.create(spotkanie);
    }
}
