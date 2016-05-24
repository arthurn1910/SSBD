package pl.lodz.p.it.ssbd2016.ssbd01.moo.managers;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManager;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.KontoMOOFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.OgloszenieFacadeLocal;

/**
 * Klasa pośrednicząca miedzy MOOEndpoint a fasadami. Przetwarza niezbędne dane.
 * Implementuje interfejs OgloszenieManagaerLocal
 */
@Stateless
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class OgloszenieManager implements OgloszenieManagerLocal {

    
    @EJB
    private KontoMOOFacadeLocal kontoFacade;
    @EJB
    private OgloszenieFacadeLocal ogloszenieFacadeLocal;
    
    @Override
    @RolesAllowed("dodajDoUlubionych")
    public void dodajDoUlubionych(Ogloszenie ogloszenie) {
        Konto konto = kontoFacade.znajdzPoLoginie("");
        Ogloszenie ogloszenieTemp = ogloszenieFacadeLocal.znajdzPoID(ogloszenie.getId());
        // Czynności związane z logiką
        ogloszenieFacadeLocal.edit(ogloszenieTemp);
        kontoFacade.edit(konto);
    }

    @Override
    @RolesAllowed("usunZUlubionych")
    public void usunZUlubionych(Ogloszenie ogloszenie) {
        Konto konto = kontoFacade.znajdzPoLoginie("");
        Ogloszenie ogloszenieTemp = ogloszenieFacadeLocal.znajdzPoID(ogloszenie.getId());
        // Czynności związane z logiką
        ogloszenieFacadeLocal.edit(ogloszenieTemp);
        kontoFacade.edit(konto);
    }
    
    @Override
    public void przydzielAgenta(Ogloszenie rowData, Konto agent) {
        Logger loger = Logger.getLogger(TrackerInterceptor.class.getName());
        Ogloszenie o = ogloszenieFacadeLocal.znajdzPoID(rowData.getId());
        Konto tmp;
        if(o.getIdAgenta()!=null){
            tmp=kontoFacade.znajdzPoLoginie(agent.getLogin());
            tmp.getOgloszenieAgentaCollection().remove(o);
            kontoFacade.edit(tmp);
        }
        tmp=kontoFacade.znajdzPoLoginie(agent.getLogin());
        o.setIdAgenta(tmp);
        ogloszenieFacadeLocal.edit(o);
        tmp.getOgloszenieAgentaCollection().add(o);
        kontoFacade.edit(tmp);
    }
}
