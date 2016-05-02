package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.io.IOException;
import java.rmi.RemoteException;
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
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;

/**
 * API servera dla modułu funkcjonalnego MOK
 */
@Stateful
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOKEndpoint implements MOKEndpointLocal, SessionSynchronization {

    @EJB
    private KontoManagerLocal kontoManager;
    @EJB
    private KontoFacadeLocal kontoFacade;
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;

    @Resource
    private SessionContext sessionContext;
    
    private long txId;
    private static final Logger loger = Logger.getLogger(MOKEndpoint.class.getName());
    
    private Konto kontoStan;
    
    @Override
    @PermitAll
    public void rejestrujKontoKlienta(Konto konto) {
        kontoManager.rejestrujKontoKlienta(konto);
    }
    
    @Override
    @RolesAllowed("utworzKonto")
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) throws Exception {
        kontoManager.utworzKonto(konto, poziomyDostepu);
    }

    @Override
    @RolesAllowed("potwierdzKonto")
    public void potwierdzKonto(Konto konto) {
        Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);
    }

    @Override
    @RolesAllowed("odblokujKonto")
    public void odblokujKonto(Konto konto) {
        Konto o = kontoFacade.find(konto.getId());
        o.setAktywne(true);
    }

    @Override
    @RolesAllowed("zablokujKonto")
    public void zablokujKonto(Konto konto) {
        Konto o = kontoFacade.find(konto.getId());
        o.setAktywne(false);
    }  
    
    @Override
    @RolesAllowed("zmienMojeHaslo")
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws Exception{        
        kontoManager.zmienMojeHaslo(kontoStan, noweHaslo, stareHaslo);
        
        kontoStan = null;
    }

    @Override
    @RolesAllowed("zmienHaslo")
    public void zmienHaslo(String noweHaslo) {
        kontoManager.zmienHaslo(kontoStan, noweHaslo);
        
        kontoStan = null;
    }

    @Override
    @RolesAllowed("znajdzPoLoginie")
    public Konto znajdzPoLoginie(String login) {
        return kontoFacade.znajdzPoLoginie(login);
    }
    
    @Override
    @RolesAllowed("pobierzPodobneKonta")
    public List<Konto> pobierzPodobneKonta(Konto konto) {
        return kontoManager.znajdzPodobne(konto);
    }
    
    @Override
    @RolesAllowed("pobierzWszystkieKonta")
    public List<Konto> pobierzWszystkieKonta() {
        return kontoManager.pobierzWszystkie();
    }
    
    @Override
    @RolesAllowed("dodajPoziomDostepu")
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.dodajPoziomDostepu(konto, poziom);
    }

    @Override
    @RolesAllowed("odlaczPoziomDostepu")
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.odlaczPoziomDostepu(konto, poziom);
    }

    @Override
    @RolesAllowed("pobierzKontoDoEdycji")
    public Konto pobierzKontoDoEdycji(Konto konto) {
        kontoStan = kontoFacade.find(konto.getId());
        try {
            return (Konto) CloneUtils.deepCloneThroughSerialization(kontoStan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @RolesAllowed("zapiszSwojeKontoPoEdycji")
    public void zapiszSwojeKontoPoEdycji(Konto konto) throws Exception {
        if (!konto.getLogin().equals(sessionContext.getCallerPrincipal().getName())) {
            throw new Exception("Nie moje konto");
        }
        
        if (kontoStan == null) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        if (!kontoStan.equals(konto)) {
            throw new IllegalArgumentException("Modyfikowane konto niezgodne z wczytanym");
        }

        kontoStan.setEmail(konto.getEmail());
        kontoStan.setImie(konto.getImie());
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setNazwisko(konto.getNazwisko());
        
        kontoFacade.edit(kontoStan);
        
        kontoStan = null;
    }
    
    @Override
    @RolesAllowed("zapiszKontoPoEdycji")
    public void zapiszKontoPoEdycji(Konto konto) {
        if (kontoStan == null) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        if (!kontoStan.equals(konto)) {
            throw new IllegalArgumentException("Modyfikowane konto niezgodne z wczytanym");
        }

        kontoStan.setEmail(konto.getEmail());
        kontoStan.setImie(konto.getImie());
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setNazwisko(konto.getNazwisko());
        
        kontoFacade.edit(kontoStan);
        
        kontoStan = null;
    }

    @Override
    @RolesAllowed("getSwojeKonto")
    public Konto getSwojeKonto() {
        String login = sessionContext.getCallerPrincipal().getName();
        return kontoFacade.znajdzPoLoginie(login);
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
}
