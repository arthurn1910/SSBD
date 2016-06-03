package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.*;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

import javax.ejb.Local;
import java.io.IOException;
import java.util.List;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOO
 */
@Local
public interface MOOEndpointLocal {

    Konto getKonto(String login);

    /**
     * Pobiera wszystkie typy ogłoszeń z tabel słownikowych
     * @return lista kluczy
     */
    List<TypOgloszenia> pobierzTypyOgloszen();

    /**
     * Pobiera wszystkie typy nieruchomości z tabel słownikowych
     * @return lista kluczy
     */
    List<TypNieruchomosci> pobierzTypyNieruchomosci();

    /**
     * Dodaje ogłszenie dla nieruchomości MOO. 1, Kamil Rogowski
     *
     * @param noweOgloszenie   ogłoszenie
     * @param nowaNieruchomosc nieruchomości
     * @param elementWyposazeniaNieruchomosci lista elementow wyposazenia nieruchomosci
     */
    void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc,  List<ElementWyposazeniaNieruchomosci> elementWyposazeniaNieruchomosci);

    /**
     * Pobiera wszystkie predefiniowane elementy dla kategorii
     * @return lista kluczy do elementow kategorii
     */
    List<ElementWyposazeniaNieruchomosci> pobierzElementyKategorii();
    /**
     * Pobiera wszystkie predefiniowane kategorie
     * @return lista kluczy do kategorii
     */
    List<KategoriaWyposazeniaNieruchomosci> pobierzKategorie();
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



}
