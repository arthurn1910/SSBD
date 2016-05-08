package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import java.rmi.RemoteException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.KontoFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.OgloszenieFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.managers.SpotkanieManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.SessionSynchronization;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;

/**
 * API servera dla modułu funkcjonalnego MOS
 */
@Stateful
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOSEndpoint implements MOSEndpointLocal, SessionSynchronization {
    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    @EJB
    private KontoFacadeLocalInMOS kontoFacade;
    @EJB
    private OgloszenieFacadeLocalInMOS ogloszenieFacade;
    @EJB
    private SpotkanieManagerLocal spotkanieManager;

    
    private long txId;
    private static final Logger loger = Logger.getLogger(MOSEndpoint.class.getName()); 
    
    @Override
    @RolesAllowed("pobierzSpotkania")
    public List<Spotkanie> pobierzSpotkania(Konto spotkaniaDlaKonta) {
        return spotkanieManager.pobierzUmowioneSpotkania(spotkaniaDlaKonta);
    }

    @Override
    @RolesAllowed("anulujSpotkanie")
    public void anulujSpotkanie(Spotkanie spotkanieDoAnulowania) {
        spotkanieFacade.remove(spotkanieDoAnulowania);
    }

    @Override
    @RolesAllowed("pobierzSpotkaniaDlaOgloszenia")
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {
        return spotkanieManager.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    @Override
    @RolesAllowed("rezerwujSpotkanie")
    public void rezerwujSpotkanie(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("pobierzSpotkanieDoEdycji")
    public Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("zapiszSpotkaniePoEdycji")
    public void zapiszSpotkaniePoEdycji(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("pobierzUmowioneSpotkania")
    public List<Spotkanie> pobierzUmowioneSpotkania(Konto konto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
