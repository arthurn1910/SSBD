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
        Collection<Ogloszenie> ogloszenieAgentaCollection;
        Logger loger = Logger.getLogger(TrackerInterceptor.class.getName());
        Ogloszenie o = ogloszenieFacadeLocal.znajdzPoID(rowData.getId());
        Konto tmp;
        if(o.getIdAgenta()!=null){
            loger.log(Level.INFO, "!!!!"+o.getIdAgenta().getId());
            tmp=kontoFacade.znajdzPoLoginie(String.valueOf(o.getIdAgenta().getId()));
            tmp.getOgloszenieAgentaCollection().remove(o);
            kontoFacade.edit(tmp);
        }
        loger.log(Level.INFO, "!!!!2");
        tmp=kontoFacade.znajdzPoLoginie(String.valueOf(agent.getId()));
        loger.log(Level.INFO, "!!!!3");
        o.setIdAgenta(tmp);
        loger.log(Level.INFO, "!!!!4");
        ogloszenieFacadeLocal.edit(o);
        loger.log(Level.INFO, "!!!!5");
        tmp.getOgloszenieAgentaCollection().add(o);
        loger.log(Level.INFO, "!!!!6");
        kontoFacade.edit(tmp);
        loger.log(Level.INFO, "!!!!7");
        ogloszenieFacadeLocal.flush();
        loger.log(Level.INFO, "!!!!8");
    }
}
