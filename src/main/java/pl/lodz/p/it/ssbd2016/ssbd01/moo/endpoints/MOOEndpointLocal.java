package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOO
 */
@Local
public interface MOOEndpointLocal {

    public Konto getKonto(String login);

    public TypOgloszenia getTypOgloszenia(String typ);

    public TypNieruchomosci getTypNieruchomosci(String typ);

    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc);

    public List<Ogloszenie> pobierzWszytkieOgloszenia();

    public void aktywujOgloszenie(Ogloszenie rowData);

    public void deaktywujOgloszenie(Ogloszenie rowData);

    public List<Ogloszenie> pobierzUlubioneOgloszenia();

    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    public void dodajDoUlubionych(Ogloszenie ogloszenie);
    
    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    public void usunZUlubionych(Ogloszenie ogloszenie);
    
}
