package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.*;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOO
 * @author java
 */
@Local
public interface MOOEndpointLocal {

    Konto getKonto(String login);

    TypOgloszenia getTypOgloszenia(String typ);

    TypNieruchomosci getTypNieruchomosci(String typ);

    /**
     * Dodaje ogłszenie dla nieruchomości MOO. 1, Kamil Rogowski
     *
     * @param noweOgloszenie   ogłoszenie
     * @param nowaNieruchomosc nieruchomości
     */
    void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc);

    List<Ogloszenie> pobierzWszytkieOgloszenia();

    void aktywujOgloszenie(Ogloszenie rowData);

    void deaktywujOgloszenie(Ogloszenie rowData);

    public List<Ogloszenie> pobierzUlubioneOgloszenia();

    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    public void dodajDoUlubionych(Ogloszenie ogloszenie);

    /***
     * Metoda przydzielająca konto agenta do ogłoszenia
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     * @param rowData
     * @param agent 
     */
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent);
    
    /***
     * Metoda zmieniająca agenta w ogłoszeniu
     * Stowrzył Radosław Pawlaczyk
     * MOO 7
     * @param rowData
     * @param agent 
     */
    public void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent);
    
    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    public void usunZUlubionych(Ogloszenie ogloszenie);

}
