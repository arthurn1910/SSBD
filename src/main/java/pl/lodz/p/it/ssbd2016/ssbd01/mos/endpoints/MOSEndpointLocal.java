package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOS
 */
@Local
public interface MOSEndpointLocal {
    
    /**
     * rezerwacja spotkania MOS 1, P. Stepien
     * @param spotkanie 
     */
    void rezerwujSpotkanie(Spotkanie spotkanie);
    
    /**
     * pobiera spotkanie do edycji MOS 2, P. Stepien
     * @param spotkanie
     * @return 
     */
    Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie);
    
    /**
     * zapisuje edytowane spotkanie po edycji MOS 2, P. Stepien
     * @param spotkanie 
     */
    void zapiszSpotkaniePoEdycji(Spotkanie spotkanie);
    
    /**
     * pobiera liste spotkan dla danego uzytkownika
     * @param konto
     * @return 
     */
    List<Spotkanie> pobierzUmowioneSpotkania(Konto konto);
    

    /**
     * Anuluje spotkanie powiąane z kontem, stworzona dla MOS.3, Kamil Rogowski
     * @param spotkanieDoAnulowania spotkanie do anulowania
     */
    void anulujSpotkanie(Spotkanie spotkanieDoAnulowania);

    /**
     * Pobiera wszystkie spotkania, stworzona dla MOS.4, Kamil Rogowski
     * @param spotkanieDlaKonta
     * @return lista spotkań
     * @author Kamil Rogowski
     */
    List<Spotkanie> pobierzSpotkania(Konto spotkanieDlaKonta);
    
    /**
     * Pobiera spotkania dla ogłoszenia, MOS.5, Kamil Rogowski
     * @param ogloszenie ogloszenie
     * @return lista spotkan
     */
    List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie);
}
