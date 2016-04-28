package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManager;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Patryk
 * API servera dla modułu funkcjonalnego MOK
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal {

    private static final Logger logger = Logger.getLogger(MOKEndpoint.class.getName());

    @EJB
    private KontoManagerLocal kontoManager;
    @EJB
    private KontoFacadeLocal kontoFacade;
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;

    private Konto kontoStan;
    @Resource
    private SessionContext sessionContext;

    @Override
    public void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        kontoFacade.create(konto);

        poziomDostepu.setKontoId(konto);
        poziomDostepuFacade.create(poziomDostepu);
        konto.getPoziomDostepuCollection().add(poziomDostepu);
    }
    
    @Override
    public void rejestrujKontoKlienta(Konto konto) {
        kontoManager.rejestrujKontoKlienta(konto);
    }
    
    @Override
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) {
        kontoManager.utworzKonto(konto, poziomyDostepu);
    }

    @Override
    public List<Konto> pobierzWszystkieKonta() {
        return kontoFacade.findAll();
    }

    @Override
    public void potwierdzKonto(Konto konto) {
        /*Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);*/
    }

    @Override
    public void odblokujKonto(Konto konto) {
        Konto o = kontoFacade.find(konto.getId());
        o.setAktywne(true);
    }

    
    /***
     * Metoda zablokowująca konto uzytkownikowi
     * @param rowData 
     */
    @Override
    public void zablokujKonto(Konto rowData) {
        /*Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(false);*/
    }
    /***
     * Funkcja logujaca uzytkownika do systemu
     * @param login
     * @param haslo
     * @return 
     */
    @Override
    public Boolean zaloguj(String login, String haslo) {
        return "a".equals(login) && "a".equals(haslo);
    }
    /***
     * Metoda dłączająca poziom dostępu Agent uzytkownikowi
     * @param konto 
     */
    @Override
    public void dolaczPoziomAgent(Konto konto) {
    }
    /***
     * Metoda dłączająca poziom dostępu Menadzer uzytkownikowi
     * @param konto 
     */
    @Override
    public void dolaczPoziomMenadzer(Konto konto) {
    }
    /***
     * Metoda dołączająca poziom dostępu Administrator uzytkownikowi
     * @param konto 
     */
    @Override
    public void dolaczPoziomAdministrator(Konto konto) {
    }
    /***
     * Metoda odłączająca poziom dostępu Agent uzytkownikowi
     * @param konto 
     */
    @Override
    public void odlaczPoziomAgent(Konto konto) {
    }
    /***
     * Metoda odłączająca poziom dostępu Menadzer uzytkownikowi
     * @param konto 
     */
    @Override
    public void odlaczPoziomMenadzer(Konto konto) {
    }
    /***
     * Metoda ołączająca poziom dostępu Administrator uzytkownikowi
     * @param konto 
     */
    @Override
    public void odlaczPoziomAdministrator(Konto konto) {
    }
    /***
     * Funkcja sprawdza czy uzytkownik posiada poziom dostępu Agent
     * @param konto
     * @return 
     */
    @Override
    public Boolean sprawdzPoziomAgent(Konto konto) {
        return true;
    }
    /***
     * Funkcja sprawdza czy uzytkownik posiada poziom dostępu Menadzer
     * @param konto
     * @return 
     */
    @Override
    public Boolean sprawdzPoziomMenadzer(Konto konto) {
        return false;
    }
    /***
     * Funkcja sprawdza czy uzytkownik posiada poziom dostępu Administrator
     * @param konto
     * @return 
     */
    @Override
    public Boolean sprawdzPoziomAdministrator(Konto konto) {
        return true;
    }
    /***
     * Funkcja pobierajaca uzytkownika
     * @return 
     */
    @Override
    public Konto pobierzUzytkownika() {
        Konto a=new Konto(new Long(1221123131), "aaa", "aaa", false, false, 0);
        a.setEmail("aaa");
        a.setTelefon("cccc");
        a.setImie("anna");
        a.setNazwisko("1111");
        return a;
    }
    /***
     * Funkcja zwraca poziomy dostępu uzytkownika
     * @param kontoUzytkownika
     * @return 
     */
    @Override
    public String pobierzPoziomy(Konto kontoUzytkownika) {
        return "Klient, Agent";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo){
        try {
            kontoManager.zmienMojeHasloJesliPoprawne(noweHaslo, stareHaslo);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MOKEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MOKEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zmienHaslo(Konto konto, String noweHaslo){
        try {
            kontoManager.zmienHaslo(konto, noweHaslo);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MOKEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MOKEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Konto znajdzPoLoginie(String login) {
        return kontoFacade.findByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Konto> pobierzWszystkieNiepotwierdzoneKonta() {
        return kontoFacade.pobierzWszystkieNiepotwierdzoneKonta();
    }
    
    @Override
    public Konto pobierzUzytkownika(String login) {
        Konto konto;
        try {
            konto = kontoFacade.znajdzPoLoginie(login);
        } catch (Exception e) {
            return null;
        }
        return konto;
    }
    
    @Override
    public List<Konto> pobierzPodobneKonta(Konto konto) {
        return kontoManager.znajdzPodobne(konto);
    }
    
    @Override
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.dodajPoziomDostepu(konto, poziom);
    }

    @Override
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.odlaczPoziomDostepu(konto, poziom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Konto pobierzKontoDoEdycji(Konto konto) {
        kontoStan = kontoFacade.find(konto.getId());
        logger.info(kontoStan.toString());

        try {
            return (Konto) CloneUtils.deepCloneThroughSerialization(kontoStan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zapiszKontoPoEdycji(Konto konto) {

        if (kontoStan == null) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        if (!kontoStan.equals(konto)) {
            throw new IllegalArgumentException("Modyfikowane konto niezgodne z wczytanym");
        }

        logger.info(kontoStan.toString());
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setImie(konto.getImie());
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setNazwisko(konto.getNazwisko());
        kontoFacade.edit(kontoStan);
        kontoStan = null;

    }


}
