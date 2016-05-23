package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.ExteriorInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;
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
@Interceptors({ExteriorInterceptor.class ,TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOOEndpoint implements MOOEndpointLocal, SessionSynchronization {
    @EJB
    private KontoManagerLocal kontoManager;
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
    
    private Ogloszenie ogloszenieStan;
    
    
    private long txId;
    private static final Logger loger = Logger.getLogger(MOOEndpoint.class.getName());    
    
    @Override
    @RolesAllowed("dodajOgloszenie")
    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc) {        
        nieruchomoscFacadeLocal.create(nowaNieruchomosc);
        ogloszenieFacadeLocal.create(noweOgloszenie);
    }
    
    @Override
    @RolesAllowed("edytujOgloszenieDotyczaceUzytkownika")
    public void edytujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenieNowe.getId());
        if(o.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascielemOgloszenia");
        }
        else {
            // zapisz dane obiektu ogloszenieNowe
            ogloszenieFacadeLocal.edit(o);
        }
    }
    
    @Override
    @RolesAllowed("deaktywujOgloszenieDotyczaceUzytkownika")
    public void deaktywujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if(o.getIdWlasciciela().getLogin().equals(loginKonta) == false) {
            throw new WyjatekSystemu("blad.nieJestesWlascicielemOgloszenia");
        }
        else if(ogloszenie.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        }
        else {
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
        ogloszenieManagerLocal.dodajDoUlubionych(ogloszenie);
    }
    
    @Override
    @RolesAllowed("usunZUlubionych")
    public void usunZUlubionych(Ogloszenie ogloszenie) {
        ogloszenieManagerLocal.usunZUlubionych(ogloszenie);
    }
   
    @Override
    @PermitAll
    public Ogloszenie znajdzOgloszeniePoID(Long id) {
        return ogloszenieFacadeLocal.find(id);
    }
    
    @Override
    @RolesAllowed("przydzielAgentaDoOgloszenia")
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent) {
        ogloszenieFacadeLocal.przydzielAgenta(rowData, agent);
        loger.log(Level.INFO, "!!!!1");
        ogloszenieFacadeLocal.flush();
        loger.log(Level.INFO, "!!!!2");
    }
    
    @Override
    @RolesAllowed("deaktywujOgloszenieInnegoUzytkownika")
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) throws WyjatekSystemu {
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if (o == null) 
        throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoDeaktywacji");
        
        if(o.getAktywne() == false) {
            throw new WyjatekSystemu("blad.ogloszenieDeaktywowaneWczesniej");
        }
        else {
            o.setAktywne(false);
            ogloszenieFacadeLocal.edit(o);
        }
    }    
    
    @Override
    @RolesAllowed("edytujOgloszenieInnegoUzytkownika")
    public void edytujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenieNowe) throws WyjatekSystemu {
        if (ogloszenieStan == null){ 
        throw new WyjatekSystemu("blad.brakWczytanegoOgloszeniaDoEdycji");
            // kopiuj dane z ogloszenia nowego do starego
        }else{
            ogloszenieFacadeLocal.edit(ogloszenieStan);
        }
    }
    
    @Override
    @RolesAllowed("pobierzOgloszenieDoEdycji")
    public Ogloszenie pobierzOgloszenieDoEdycji(Ogloszenie ogloszenie) throws WyjatekSystemu, IOException, ClassNotFoundException{
 //       if(sessionContext.getCallerPrincipal().getName().equals(ogloszenie.getIdWlasciciela().getLogin()) == false) {
 //           throw new WyjatekSystemu("blad.nieJestesWlascicielemOgloszenia");
 //       }
        ogloszenieStan = ogloszenieFacadeLocal.find(ogloszenie.getId());
        return (Ogloszenie) CloneUtils.deepCloneThroughSerialization(ogloszenieStan);
    }
        
    //Implementacja SessionSynchronization
    /**
     * Metoda logująca czas rozpoczęcia transakcji
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
     * @throws EJBException
     * @throws RemoteException 
     */
    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " przed zakonczeniem");
    }
    
    /**
     * Metoda logująca stan zakończonej transakcji
     * @param committed     stan zakończonej transakcji
     * @throws EJBException
     * @throws RemoteException 
     */
    @Override
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + txId + " zostala zakonczona przez: " + (committed?"zatwierdzenie":"wycofanie"));
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

    @Override
    public Boolean czyPosiadaAgenta(Ogloszenie ogloszenie, Konto rowData) {
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if(o.getIdAgenta().getId().equals(rowData.getId()))
            return true;
        return false;
    }
    
    @Override
    public Boolean czyPosiadaJakiegosAgenta(Ogloszenie ogloszenie) {
        Ogloszenie o = ogloszenieFacadeLocal.find(ogloszenie.getId());
        if(o.getIdAgenta()!=null)
            return true;
        return false;
    }
    /***
     * Funckja zwracająca liste agentów
     * @return 
     */
    @Override
    public List<Konto> getAgenci() {
        List<Konto> tmp=kontoManager.pobierzWszystkie();
        List<Konto> tmp2=new ArrayList<>();
        for(Konto k : tmp){
            for(PoziomDostepu p :k.getPoziomDostepuCollection())
                if(p.getPoziom().equals("AGENT"))
                    tmp2.add(k);
        }           
        return tmp2;
    }
}
