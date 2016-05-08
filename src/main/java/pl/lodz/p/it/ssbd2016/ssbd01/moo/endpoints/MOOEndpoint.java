package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
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
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

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

    @Override
    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc) {
        
        nieruchomoscFacadeLocal.create(nowaNieruchomosc);
        ogloszenieFacadeLocal.create(noweOgloszenie);
    }
    
    /*
        @param ogloszenieNowe obiekt Ogloszenie o id starego ogłoszenia, ale zawierające nowe dane
    */
    @Override
    public void edytujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        if(ogloszenieNowe.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascielemOgloszenia");
        }
        else {
            // zapisz dane obiektu ogloszenieNowe
        }
    }
    
    /*
        @param ogloszenie, które ma zostać deaktywowane
    */
    @Override
    public void deaktywujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        if(ogloszenie.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascicielemOgloszenia");
        }
        else if(ogloszenie.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        }
        else {
            ogloszenie.setAktywne(false);
        }
    }

    @Override
    public List<Ogloszenie> pobierzWszytkieOgloszenia() {
        return ogloszenieFacadeLocal.findAll();
    }

    @Override
    public void aktywujOgloszenie(Ogloszenie rowData) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setAktywne(true);
    }

    @Override
    public void deaktywujOgloszenie(Ogloszenie rowData) throws WyjatekSystemu{
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        if(o.getAktywne())
            o.setAktywne(false);
        else 
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej"); 
    }


    @Override
    public List<Ogloszenie> pobierzUlubioneOgloszenia() {
        Konto l = kontoFacade.znajdzPoLoginie("janusz");
        return (List<Ogloszenie>) l.getOgloszenieUlubioneCollection();
    }

    @Override
    public void dodajDoUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManagerLocal.dodajDoUlubionych(ogloszenie);
    }
    
    @Override
    public void usunZUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManagerLocal.usunZUlubionych(ogloszenie);
    }
   
    @Override
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setIdAgenta(agent);
        Collection<Ogloszenie> ogloszenieAgentaCollection=agent.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.add(o);
        agent.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
    }

    @Override
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
     /**
        @param ogloszenie innego uzytkownika, które ma zostać deaktywowane
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
    */
    @Override
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        if (ogloszenie == null) 
        throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoDeaktywacji");
        
        if(ogloszenie.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        }
        else {
            ogloszenie.setAktywne(false);
        }
    }
     /**
     *
     * metoda odpowiadajaca za edycje ogloszenia innego uzytkownika 
    */   
    @Override
    public void edytujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        if (ogloszenieNowe == null) 
        throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoEdycji");
            // kopiuj dane z ogloszenia nowego do starego
        } 
}
