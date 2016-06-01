package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.io.IOException;
import javax.ejb.Local;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ElementWyposazeniaNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOO
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
    
    /**
     * Metoda aktywuje ogłoszenie
     * @param rowData 
     */
    void aktywujOgloszenie(Ogloszenie rowData);
    
    /**
     * Metoda deaktywuje ogłoszenie
     * @param rowData
     * @throws WyjatekSystemu 
     */
    public void deaktywujOgloszenie(Ogloszenie rowData) throws WyjatekSystemu;

    /**
     * Metoda edytuje ogloszenie dotyczace klienta MOO.2
     * @param ogloszenieNowe
     * @throws Exception 
     */
    public void edytujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenieNowe) throws Exception;    
    
    /**
     * Metoda odpowiadajaca za edycje ogloszenia danego uzytkownika MOO.3
     * @param ogloszenieNowe
     * @throws java.lang.Exception
    */   
    public void edytujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenieNowe) throws Exception;    
    
    /**
     * Metoda deaktywuje ogłoszenie dotyczace użytkownika MOO.4
     * @param ogloszenie
     * @throws Exception 
     */
    public void deaktywujOgloszenieDotyczaceUzytkownika(Ogloszenie ogloszenie) throws Exception;
        
    /**
    * Metoda odpowiadajaca za deaktywacje ogłoszenia innego uzytkownika MOO.5
    * @param ogloszenie do deaktywacji
    * @throws java.lang.Exception
    */   
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) throws Exception;
    
    /***
     * Metoda przydzielająca konto agenta do ogłoszenia
     * Stowrzył Radosław Pawlaczyk
     * MOO.6
     * @param rowData
     * @param agent 
     */
    public void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent);
    
    /***
     * Funkcja zwracająca ogłoszenie o wybranym ID
     * @param ID
     * @return 
     */
    public Ogloszenie znajdzPoID(Long ID);
    
    /***
     * Metoda zmieniająca agenta w ogłoszeniu
     * Stowrzył Radosław Pawlaczyk
     * MOO.7
     * @param rowData
     * @param agent 
     */
    public void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent);
    
    /**
     * Metoda pobiera wszystkie ogłoszenia MOO.8 i 9
     * @return lista ogłoszeń
     */
    List<Ogloszenie> pobierzWszytkieOgloszenia();
        
    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika MOO.11
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    public void dodajDoUlubionych(Ogloszenie ogloszenie);
    
    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika MOO.12
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    public void usunZUlubionych(Ogloszenie ogloszenie);    

    /**
     * Metoda pobierająca ogłoszenie do edycji. Zapewnia blokade optymistyczną.
     * @param ogloszenie ogloszenie do edycji
     * @return 
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu 
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public Ogloszenie pobierzOgloszenieDoEdycji(Ogloszenie ogloszenie) throws WyjatekSystemu, IOException, ClassNotFoundException;
         
    /**
     * Pobiera liste agentów
     * @return lista agentow
     */ 
    public List<Konto> pobierzListeAgentow();
    
    /**
     * Zwraca login aktualnie zalogowanego użytkownika
     * @return login użytkownika
     */
    public String pobierzZalogowanegoUzytkownika();
    
    /**
     * Zwraca wyposażenie nieruchomości
     * @param idNieruchomosci id nieruchomości
     * @return wyposażenie
     */
    public List<ElementWyposazeniaNieruchomosci> pobierzWyposazenieNieruchomosci(Long idNieruchomosci);
    
    /**
     * Zwraca wszystkie możliwe wyposażenia nieruchomości
     * @return wyposażenie
     */
    public List<ElementWyposazeniaNieruchomosci> getWszystkieMozliweElementyWyposazeniaNieruchomosci();
}
