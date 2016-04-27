package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;

/**
 * API servera dla modułu funkcjonalnego MOK
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal{
    
    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private KontoManagerLocal kontoManager;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;
   
    private Konto kontoStan;
    
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
    public List<Konto> pobierzWszystkieKonta() {
        return kontoFacade.findAll();
    }

    @Override
    public void potwierdzKonto(Konto konto) {
        /*Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);*/
    }
    /***
     * Metoda odblokowująca konto uzytkownikowi
     * @param rowData 
     */
    @Override
    public void odblokujKonto(Konto rowData) {
        /*Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(true);*/
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
}
