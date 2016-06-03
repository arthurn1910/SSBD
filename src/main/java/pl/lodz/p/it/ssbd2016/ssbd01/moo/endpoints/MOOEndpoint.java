package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.*;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.*;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.managers.OgloszenieManagerLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * API servera dla modułu funkcjonalnego MOO
 */
@Stateful
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOOEndpoint implements MOOEndpointLocal, SessionSynchronization {

    @EJB
    private OgloszenieManagerLocal ogloszenieManager;
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
    @EJB
    private ElementWyposazeniaNieruchomosciFacadeLocal elementWyposazeniaNieruchomosciFacade;
    @EJB
    private KategoriaWyposazeniaNieruchomosciFacadeLocal kategoriaWyposazeniaNieruchomosciFacade;
    @Resource
    private SessionContext sessionContext;

    private Ogloszenie ogloszenieStan;


    private long txId;
    private static final Logger loger = Logger.getLogger(MOOEndpoint.class.getName());

    @Override
    @RolesAllowed("dodajOgloszenie")
    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc, List<ElementWyposazeniaNieruchomosci> elementWyposazeniaNieruchomosci) {

        ogloszenieFacadeLocal.create(noweOgloszenie);
        ogloszenieFacadeLocal.flush();
        for (ElementWyposazeniaNieruchomosci wyposazeniaNieruchomosci : elementWyposazeniaNieruchomosci) {
            elementWyposazeniaNieruchomosciFacade.edit(wyposazeniaNieruchomosci);
        }
        elementWyposazeniaNieruchomosciFacade.flush();
        ogloszenieManager.przeliczAgregat();
    }

    @Override
    public List<ElementWyposazeniaNieruchomosci> pobierzElementyKategorii() {
        return elementWyposazeniaNieruchomosciFacade.findAll();
    }

    @Override
    public List<KategoriaWyposazeniaNieruchomosci> pobierzKategorie() {
        return kategoriaWyposazeniaNieruchomosciFacade.findAll();
    }

    @Override
    @RolesAllowed("edytujOgloszenieDotyczaceUzytkownika")
    public void edytujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenieNowe.getId());
        if (o.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascielemOgloszenia");
        } else {
            // zapisz dane obiektu ogloszenieNowe
            ogloszenieFacadeLocal.edit(o);
        }
    }

    @Override
    @RolesAllowed("deaktywujOgloszenieDotyczaceUzytkownika")
    public void deaktywujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if (o.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascicielemOgloszenia");
        } else if (ogloszenie.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        } else {
            o.setAktywne(false);
            ogloszenieFacadeLocal.edit(o);
        }
    }

    @Override
    @RolesAllowed("deaktywujOgloszenie")
    public void deaktywujOgloszenie(Ogloszenie rowData) throws WyjatekSystemu {
    }

    @Override
    @RolesAllowed("pobierzListeAgentow")
    public List<Konto> pobierzListeAgentow() {
        return kontoFacade.findAll();
    }

    @Override
    @PermitAll
    public List<Ogloszenie> pobierzWszytkieOgloszenia() {
        return ogloszenieFacadeLocal.findAll();
    }

    @Override
    @RolesAllowed("aktywujOgloszenie")
    public void aktywujOgloszenie(Ogloszenie rowData) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setAktywne(true);
    }

    @Override
    @RolesAllowed("dodajDoUlubionych")
    public void dodajDoUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManager.dodajDoUlubionych(ogloszenie);
    }

    @Override
    @RolesAllowed("usunZUlubionych")
    public void usunZUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManager.usunZUlubionych(ogloszenie);
    }

    @Override
    @RolesAllowed("znajdzPoID")
    public Ogloszenie znajdzPoID(Long id) {
        return ogloszenieFacadeLocal.znajdzPoID(id);
    }

    @Override
    @RolesAllowed("przydzielAgentaDoOgloszenia")
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setIdAgenta(agent);
        Collection<Ogloszenie> ogloszenieAgentaCollection = agent.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.add(o);
        agent.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
    }

    @Override
    @RolesAllowed("zmienAgentaWOgloszeniu")
    public void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        Konto agentStary = o.getIdAgenta();
        o.setIdAgenta(agent);
        Collection<Ogloszenie> ogloszenieAgentaCollection = agent.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.add(o);
        agent.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
        ogloszenieAgentaCollection = agentStary.getOgloszenieAgentaCollection();
        ogloszenieAgentaCollection.remove(o);
        agentStary.setOgloszenieAgentaCollection(ogloszenieAgentaCollection);
    }

    @Override
    @RolesAllowed("deaktywujOgloszenieInnegoUzytkownika")
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if (o == null)
            throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoDeaktywacji");

        if (o.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        } else {
            o.setAktywne(false);
            ogloszenieFacadeLocal.edit(o);
        }
    }


    @Override
    public List<TypOgloszenia> pobierzTypyOgloszen() {
        return typOgloszeniaFacade.findAll();
    }

    @Override
    public List<TypNieruchomosci> pobierzTypyNieruchomosci() {
        return typNieruchomosciFacade.findAll();
    }

    @Override
    @RolesAllowed("edytujOgloszenieInnegoUzytkownika")
    public void edytujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        if (ogloszenieStan == null) {
            throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoEdycji");
            // kopiuj dane z ogloszenia nowego do starego
        } else {
            ogloszenieFacadeLocal.edit(ogloszenieStan);
        }
    }

    @Override
    @RolesAllowed("pobierzOgloszenieDoEdycji")
    public Ogloszenie pobierzOgloszenieDoEdycji(Ogloszenie ogloszenie) throws WyjatekSystemu, IOException, ClassNotFoundException {
        //       if(sessionContext.getCallerPrincipal().getName().equals(ogloszenie.getIdWlasciciela().getLogin()) == false) {
        //           throw new WyjatekSystemu("blad.nieJestesWlascicielemOgloszenia");
        //       }
        ogloszenieStan = ogloszenieFacadeLocal.find(ogloszenie.getId());
        return (Ogloszenie) CloneUtils.deepCloneThroughSerialization(ogloszenieStan);
    }

    //Implementacja SessionSynchronization

    /**
     * Metoda logująca czas rozpoczęcia transakcji
     *
     * @throws EJBException
     * @throws RemoteException
     */
    @Override
    public void afterBegin() throws EJBException, RemoteException {
        txId = System.currentTimeMillis();
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " zostala rozpoczeta");
    }

    /**
     * Metoda logująca czas przed zakończeniem transakcji
     *
     * @throws EJBException
     * @throws RemoteException
     */
    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " przed zakonczeniem");
    }

    /**
     * Metoda logująca stan zakończonej transakcji
     *
     * @param committed stan zakończonej transakcji
     * @throws EJBException
     * @throws RemoteException
     */
    @Override
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " zostala zakonczona przez: " + (committed ? "zatwierdzenie" : "wycofanie"));
    }

    // gettery i settery

    @Override
    public Konto getKonto(String login) {
        return kontoFacade.znajdzPoLoginie(login);
    }

}
