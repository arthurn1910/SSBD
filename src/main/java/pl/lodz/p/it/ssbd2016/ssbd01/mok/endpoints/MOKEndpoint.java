package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.AdresIpServiceLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.NotyfikacjaServiceLocal;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.interceptor.Interceptors;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

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
    @EJB
    private AdresIpServiceLocal adresIpService;
    @EJB
    private NotyfikacjaServiceLocal notyfikacjaService;
    @Resource
    private SessionContext sessionContext;
    
    private long txId;
    private static final Logger loger = Logger.getLogger(MOKEndpoint.class.getName());
    
    private Konto kontoStan;
    
    @Override
    @PermitAll
    public void rejestrujKontoKlienta(Konto konto) throws WyjatekSystemu{
        kontoManager.rejestrujKontoKlienta(konto);
        notyfikacjaService.wyslijPowiadomienieRejestracji(konto);
    }
    
    @Override
    @RolesAllowed("utworzKonto")
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) throws WyjatekSystemu{
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
        notyfikacjaService.wyslijPowiadomienieAktywacjiKonta(konto);
    }

    @Override
    @RolesAllowed("zablokujKonto")
    public void zablokujKonto(Konto konto) {
        Konto o = kontoFacade.find(konto.getId());
        o.setAktywne(false);
        notyfikacjaService.wyslijPowiadomienieZablokowaniaKonta(konto);
    }  
    
    @Override
    @RolesAllowed("zmienMojeHaslo")
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws WyjatekSystemu{        
        kontoManager.zmienMojeHaslo(kontoStan, noweHaslo, stareHaslo);
        
        kontoStan = null;
    }

    @Override
    @RolesAllowed("zmienHaslo")
    public void zmienHaslo(String noweHaslo) throws WyjatekSystemu{
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
    public void dodajPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu{
        kontoManager.dodajPoziomDostepu(konto, poziom);
    }

    @Override
    @RolesAllowed("odlaczPoziomDostepu")
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu{
        kontoManager.odlaczPoziomDostepu(konto, poziom);
    }

    @Override
    @RolesAllowed("pobierzKontoDoEdycji")
    public Konto pobierzKontoDoEdycji(Konto konto) throws WyjatekSystemu{
        kontoStan = kontoFacade.find(konto.getId());
        return (Konto) CloneUtils.deepCloneThroughSerialization(kontoStan);
    }

    @Override
    @RolesAllowed("zapiszSwojeKontoPoEdycji")
    public void zapiszSwojeKontoPoEdycji(Konto konto) throws WyjatekSystemu{
        if (!konto.getLogin().equals(sessionContext.getCallerPrincipal().getName())) {
            throw new WyjatekSystemu("niezgodnyLogin");
        }
        
        if (kontoStan == null) {
            throw new WyjatekSystemu("brakKontaDoEdycji");
        }
        if (!kontoStan.equals(konto)) {
            throw new WyjatekSystemu("kontoNiezgodneZWczytanym");
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
    public void zapiszKontoPoEdycji(Konto konto)  throws WyjatekSystemu{
        if (kontoStan == null) {
            throw new WyjatekSystemu("brakKontaDoEdycji");
        }
        if (!kontoStan.equals(konto)) {
            throw new WyjatekSystemu("kontoNiezgodneZWczytanym");
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
    public Konto getSwojeKonto() throws WyjatekSystemu{
        String login;
        try{
            login = sessionContext.getCallerPrincipal().getName();
        }catch(EJBAccessException ex){
            throw new WyjatekSystemu("blad.brakDostepu");
        }
        return kontoFacade.znajdzPoLoginie(login);

    }

    @Override
    @RolesAllowed("pobierzHistorieLogowanUzytkownikow")
    public List<HistoriaLogowania> pobierzHistorieLogowanUzytkownikow() {
        return adresIpService.pobierzHistorieLogowanUzytkownikow();
    }

    @Override
    @PermitAll
    public void ustawIP(String login){
        adresIpService.processIpAdress(login);
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
