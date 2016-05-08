package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.NieruchomoscFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.OgloszenieFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.TypNieruchomosciFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.TypOgloszeniaFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.KontoMOOFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.managers.OgloszenieManagerLocal;

/**
 * API servera dla modułu funkcjonalnego MOO
 */
@Stateful
public class MOOEndpoint implements MOOEndpointLocal {

    @EJB
    private OgloszenieManagerLocal ogloszenieManagerLocal;
    @EJB
    private KontoMOOFacadeLocal kontoFacade;
    @EJB
    private TypOgloszeniaFacadeLocal typOgloszeniaFacade;
    @EJB
    private TypNieruchomosciFacadeLocal typNieruchomosciFacade;
    @EJB
    private OgloszenieFacadeLocal ogloszenieFacadeLocal;
    @EJB
    private NieruchomoscFacadeLocal nieruchomoscFacadeLocal;
    @Resource
    private SessionContext sessionContext;
    
    
    @Override
    @RolesAllowed("dodajOgloszenie")
    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc) {
        
        nieruchomoscFacadeLocal.create(nowaNieruchomosc);
        ogloszenieFacadeLocal.create(noweOgloszenie);
    }
    
    @Override
    @RolesAllowed("edytujOgloszenieDotyczaceUzytkownika")
    public void edytujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenieNowe) throws Exception {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        if(ogloszenieNowe.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new Exception("Nie jestes wlascicielem tego ogloszenia");
        }
        else {
            // zapisz dane obiektu ogloszenieNowe
        }
    }
    
    @Override
    @RolesAllowed("deaktywujOgloszenieDotyczaceUzytkownika")
    public void deaktywujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenie) throws Exception {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        if(ogloszenie.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new Exception("Nie jestes wlascicielem tego ogloszenia");
        }
        else if(ogloszenie.getAktywne() == false) {
            throw new Exception("Ogloszenie juz zostalo deaktywowane");
        }
        else {
            ogloszenie.setAktywne(false);
        }
    }

    @Override
    @PermitAll
    public List<Ogloszenie> pobierzWszytkieOgloszenia() {
        return ogloszenieFacadeLocal.findAll();
    }

    @Override
    @RolesAllowed("dodajDoUlubionych")
    public void dodajDoUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManagerLocal.dodajDoUlubionych(ogloszenie);
    }
    
    @Override
    @RolesAllowed("usunZUlubionych")
    public void usunZUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManagerLocal.usunZUlubionych(ogloszenie);
    }
   
    @Override
    @RolesAllowed("przydzielAgentaDoOgloszenia")
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setIdAgenta(agent);
        Collection<Ogloszenie> ogloszenieAgentaCollection=agent.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.add(o);
        agent.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
    }

    @Override
    @RolesAllowed("zmienAgentaWOgloszeniu")
    public void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        Konto agentStary=o.getIdAgenta();
        o.setIdAgenta(agent);
        Collection<Ogloszenie> ogloszenieAgentaCollection=agent.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.add(o);
        agent.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
        ogloszenieAgentaCollection=agentStary.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.remove(o);
        agentStary.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
    }
    
    @Override
    @RolesAllowed("deaktywujOgloszenieInnegoUzytkownika")
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) throws Exception {
        if (ogloszenie == null) 
        throw new IllegalArgumentException("Brak wczytanego ogloszenia do deaktywacji");
        
        if(ogloszenie.getAktywne() == false) {
            throw new Exception("Ogloszenie juz zostalo deaktywowane");
        }
        else {
            ogloszenie.setAktywne(false);
        }
    }    
    
    @Override
    @RolesAllowed("edytujOgloszenieInnegoUzytkownika")
    public void edytujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenieNowe) throws Exception {
        if (ogloszenieNowe == null) 
        throw new IllegalArgumentException("Brak wczytanego ogloszenia do edycji");
            // kopiuj dane z ogloszenia nowego do starego
    }
    
    @Override
    @PermitAll
    public Ogloszenie pobierzOgłoszenie(Ogloszenie ogloszenie) {
        return ogloszenieFacadeLocal.find(ogloszenie.getId());
    }
    
    // gettery i settery
    
    @Override
    public Konto getKonto(String login) {
        return kontoFacade.znajdzPoLoginie(login);
    }

    @Override
    public TypOgloszenia getTypOgloszenia(String typ) {
        return typOgloszeniaFacade.znajdzPoNazwie(typ);
    }

    @Override
    public TypNieruchomosci getTypNieruchomosci(String typ) {
        return typNieruchomosciFacade.znajdzPoNazwie(typ);
    }
}
